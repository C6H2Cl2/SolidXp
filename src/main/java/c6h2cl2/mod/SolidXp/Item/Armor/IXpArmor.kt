package c6h2cl2.mod.SolidXp.Item.Armor

interface IXpArmor {
    //倍率。％単位。
    val armorShield: Float
    //一式揃った時のボーナス。倍率。パーセント単位。
    val shieldBonus: Float
    //上限。倍率。パーセント単位。
    val shieldLimit: Float
}
