package the_gatherer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import the_gatherer.GathererMod;
import the_gatherer.cards.Helper.AbstractTaggedCard;
import the_gatherer.patches.CardColorEnum;
import the_gatherer.powers.SpareBottlePower;

public class SpareBottle extends AbstractTaggedCard {
	private static final String RAW_ID = "SpareBottle";
	public static final String ID = GathererMod.makeID(RAW_ID);
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String IMG = GathererMod.GetCardPath(RAW_ID);
	public static final String[] IMGS = new String[]{
			GathererMod.GetCardPath(RAW_ID),
			GathererMod.GetCardPath(RAW_ID + "_S"),
			GathererMod.GetCardPath(RAW_ID + "_G")
	};
	private static final int COST = 1;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final CardType TYPE = CardType.POWER;
	private static final CardColor COLOR = CardColorEnum.GATHERER_LIME;
	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.SELF;

	private static final int POWER = 4;
	private static final int UPGRADE_BONUS = 2;

	@Override
	public void setTag(int tag) {
		this.baseMagicNumber = tag + POWER;
		magicNumber = baseMagicNumber;
		if (upgraded) {
			upgradeEffect();
		}
		if (tag > 0) {
			this.loadCardImage(IMGS[tag]);
			this.textureImg = IMGS[tag];
		}

		super.setTag(tag);
	}

	public String getTagName(int tag) {
		if (tag < 0)
			return "<?>";
		return "<" + EXTENDED_DESCRIPTION[tag] + ">";
	}

	public SpareBottle() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = POWER;
		this.magicNumber = this.baseMagicNumber;
		updateTagDescription();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpareBottlePower(p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new SpareBottle();
	}

	public void upgradeEffect() {
		upgradeMagicNumber(UPGRADE_BONUS);
	}

	public static SpareBottle makeTransformedCopy(AbstractCard c) {
		SpareBottle sb = new SpareBottle();
		switch (c.rarity) {
			case RARE:
				sb.misc = 2;
				break;
			case UNCOMMON:
				sb.misc = 1;
				break;
			default:
				sb.misc = 0;
				break;
		}
		if (c.upgraded) {
			sb.upgrade();
		}
		sb.setTag(sb.misc);
		return sb;
	}
}
