package the_gatherer.potions;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import the_gatherer.patches.PotionRarityEnum;

public class LesserAttackPotion extends AbstractPotion {
	public static final String POTION_ID = "LesserAttackPotion";
	private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
	public static final String NAME = potionStrings.NAME;
	public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

	public LesserAttackPotion() {
		super(NAME, POTION_ID, PotionRarityEnum.LESSER, PotionSize.CARD, PotionColor.FIRE);
		this.potency = this.getPotency();
		this.description = DESCRIPTIONS[0];
		this.isThrown = false;
		this.tips.add(new PowerTip(this.name, this.description));
	}

	public void use(AbstractCreature target) {
		AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
		c.setCostForTurn(0);
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
	}

	public AbstractPotion makeCopy() {
		return new LesserAttackPotion();
	}

	public int getPotency(int ascensionLevel) {
		return 1;
	}
}
