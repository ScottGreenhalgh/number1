package me.cazmik.number1.commands;

import me.cazmik.number1.Number1;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.util.EulerAngle;


public class ArmorStandCommand implements CommandExecutor {

    private final Number1 plugin;

    public ArmorStandCommand(Number1 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info("Run this command in game.");
            return true;
        }
        Player player = (Player) sender;

        //create items for armorstand with meta
        ItemStack senderSkull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta senderSkullMeta = (SkullMeta) senderSkull.getItemMeta();
        senderSkullMeta.setOwningPlayer(player);
        senderSkull.setItemMeta(senderSkullMeta);

        ItemStack handBow = new ItemStack(Material.BOW, 1);
        ItemMeta handBowMeta = handBow.getItemMeta();
        handBowMeta.setEnchantmentGlintOverride(true);
        handBow.setItemMeta(handBowMeta);
        /*
        ItemStack chestplateArmor = new ItemStack(Material.NETHERITE_CHESTPLATE, 1);
        ItemStack leggingsArmor = new ItemStack(Material.NETHERITE_LEGGINGS, 1);
        ItemStack bootsArmor = new ItemStack(Material.NETHERITE_BOOTS, 1);
        ArmorTrim redeyeTrim = new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.EYE);

        ArmorMeta chestplateArmorMeta = (ArmorMeta) chestplateArmor.getItemMeta();
        chestplateArmorMeta.setTrim(redeyeTrim);
        chestplateArmor.setItemMeta(chestplateArmorMeta);

        ArmorMeta leggingsArmorMeta = (ArmorMeta) leggingsArmor.getItemMeta();
        leggingsArmorMeta.setTrim(redeyeTrim);
        leggingsArmor.setItemMeta(leggingsArmorMeta);

        ArmorMeta bootsArmorMeta = (ArmorMeta) bootsArmor.getItemMeta();
        bootsArmorMeta.setTrim(redeyeTrim);
        bootsArmor.setItemMeta(bootsArmorMeta);
        */
        //define armorstand location
        ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        //set equipment
        armorstand.setHelmet(senderSkull);
        armorstand.setChestplate(getTrimmedArmor(Material.NETHERITE_CHESTPLATE));
        armorstand.setLeggings(getTrimmedArmor(Material.NETHERITE_LEGGINGS));
        armorstand.setBoots(getTrimmedArmor(Material.NETHERITE_BOOTS));
        armorstand.setItemInHand(handBow);
        //set additional features
        armorstand.setArms(true);
        armorstand.setGlowing(true);
        //set body part angles
        armorstand.setHeadPose(new EulerAngle(Math.toRadians(345), Math.toRadians(35), 0));
        armorstand.setLeftLegPose(new EulerAngle(Math.toRadians(32), 0, 0));
        armorstand.setRightLegPose(new EulerAngle(Math.toRadians(329), 0, 0));
        armorstand.setLeftArmPose(new EulerAngle(Math.toRadians(306), Math.toRadians(333), 0));
        armorstand.setRightArmPose(new EulerAngle(Math.toRadians(248), 0, Math.toRadians(297)));

        player.sendMessage("Creating Armorstand.");

        return true;
    }
    //shorten duplicate code
    public ItemStack getTrimmedArmor(Material material) {
        ItemStack armor = new ItemStack(material, 1);
        ArmorMeta meta = (ArmorMeta) armor.getItemMeta();
        meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.EYE));
        armor.setItemMeta(meta);
        return armor;
    }
}
