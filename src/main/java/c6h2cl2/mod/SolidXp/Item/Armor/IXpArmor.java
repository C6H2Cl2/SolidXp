package c6h2cl2.mod.SolidXp.Item.Armor;

/**
 * Created by C6H2Cl2 on 2016/09/06.
 */
public interface IXpArmor {
    //倍率。％単位。
    float getArmorShield();
    //一式揃った時のボーナス。倍率。パーセント単位。
    float getShieldBonus();
    //上限。倍率。パーセント単位。
    float getShieldLimit();
}
