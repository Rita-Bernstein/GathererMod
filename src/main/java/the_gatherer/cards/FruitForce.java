package the_gatherer.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import the_gatherer.GathererMod;
import the_gatherer.interfaces.OnObtainEffect;
import the_gatherer.patches.CardColorEnum;

public class FruitForce extends CustomCard implements OnObtainEffect {
	private static final String RAW_ID = "FruitForce";
	public static final String ID = GathererMod.makeID(RAW_ID);
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String IMG = GathererMod.GetCardPath(RAW_ID);
	private static final int COST = 1;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final CardType TYPE = CardType.ATTACK;
	private static final CardColor COLOR = CardColorEnum.GATHERER_LIME;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;

	private static final int POWER = 10;
	private static final int UPGRADE_BONUS = 5;
	private static final int HP_GAIN = 4;

	private int prevMaxHP = 0;

	public FruitForce() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = POWER;
		this.magicNumber = this.baseMagicNumber;

		updateDamage(true);
	}

	private void updateDamage(boolean ignoreUpdate) {
		if (AbstractDungeon.player != null && (ignoreUpdate || this.prevMaxHP != AbstractDungeon.player.maxHealth)) {
			this.baseDamage = (AbstractDungeon.player.maxHealth * this.magicNumber) / 100;
			if (prevMaxHP == 0) {
				this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
				this.initializeDescription();
			}
			prevMaxHP = AbstractDungeon.player.maxHealth;
		}
	}

	public void applyPowers() {
		updateDamage(true);
		super.applyPowers();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
	}

	public AbstractCard makeCopy() {
		return new FruitForce();
	}

	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_BONUS);
			updateDamage(true);
		}
	}

	@Override
	public void update() {
		super.update();

		updateDamage(false);
	}

	@Override
	public void onObtain() {
		AbstractDungeon.player.increaseMaxHp(HP_GAIN, false);
	}
}
