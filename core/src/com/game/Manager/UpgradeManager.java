package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.Entity.Base;
import com.game.Entity.Upgrade;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class UpgradeManager {
    private Upgrade uFork, uScythe, uDagger, uSword, uBattleAxe, uMace, uBow, uCrossbow, uCannon, uCannonBall, uBetterCannon, uSceptre, uBook, uGear, uSonar, uHealth, uBetterHealth, uBetterBetterHealth, uRegeneration, uShield, uGold, uBetterGold, uDiamonds, uBetterDiamonds, uDiscount10, uDiscount20, uDiscount30, uUpgrade, uHammer, uBetterUpgrade, uLuck;
    private Skin images_upgrades;
    private Table table_upgrade;
    private Dialog upgradeDialog;
    private LanguageManager languageManager;

    private TextTooltip.TextTooltipStyle textTooltipStyle;
    private TextTooltip tooltip;

    private Base base;

    private ArrayList<Upgrade> upgradeList;

    private JSONObject upgrades;

    private JSONArray unlockedUpgrades;

    public UpgradeManager(LanguageManager languageManager, BitmapFont font, Base base, JSONObject upgrades, JSONArray unlockedUpgrades){
        this.images_upgrades = new Skin(new TextureAtlas("assets/icons/upgrade_icons.pack"));;
        this.languageManager = languageManager;
        this.base = base;
        this.upgrades = upgrades;
        this.unlockedUpgrades = unlockedUpgrades;

        Drawable tooltipBackground = new TextureRegionDrawable(new TextureRegion(new Texture(new FileHandle("assets/dialog/settings_dialog.png"))));
        textTooltipStyle = new TextTooltip.TextTooltipStyle();
        textTooltipStyle.label = new Label.LabelStyle(font, Color.WHITE);
        textTooltipStyle.background = tooltipBackground;
        textTooltipStyle.wrapWidth = 400; //nie może być 100.0f

        initUpgrades();
        initListeners();

        createUpgradeTable();
        loadUpgrades();

    }
    public Table returnUpgradeTable(){
        return  table_upgrade;
    }

    public void createUpgradeTable(){
        this.table_upgrade = new Table();
        table_upgrade.setBounds(0,0,Gdx.graphics.getWidth()/10*8, Gdx.graphics.getHeight()/10*7);
        table_upgrade.add(uFork.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uScythe.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uDagger.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uSword.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uBattleAxe.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uMace.getImage());

        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(uBow.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uCrossbow.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(uSceptre.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uBook.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(uCannon.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uCannonBall.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uBetterCannon.getImage());

        table_upgrade.row().padTop(10);
        table_upgrade.add(uGear.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uSonar.getImage());

        table_upgrade.row().padTop(10);
        table_upgrade.add(uHealth.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uBetterHealth.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uRegeneration.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uShield.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uBetterBetterHealth.getImage());
        table_upgrade.row();

        table_upgrade.row().padTop(10);
        table_upgrade.add(uGold.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uBetterGold.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uDiamonds.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uBetterDiamonds.getImage());
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(uDiscount10.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uDiscount20.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uDiscount30.getImage());
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(uUpgrade.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uHammer.getImage());
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(uBetterUpgrade.getImage());
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(uLuck.getImage());
        table_upgrade.padBottom(16);
    }

    public Dialog returnUpgradeDialog(){
        return upgradeDialog;
    }
    public void setUpgradeDialog(Dialog upgradeDialog, Table table_upgrade, Label.LabelStyle labelStyle){
        this.upgradeDialog = upgradeDialog;
        upgradeDialog.setBounds(0,0, Gdx.graphics.getWidth()/10*8,Gdx.graphics.getHeight()/10*8);
        upgradeDialog.text(languageManager.getValue(languageManager.getLanguage(), "upgrade_dialog_field_text"), labelStyle);
        upgradeDialog.add(table_upgrade);
        upgradeDialog.row();
    }

    private void initUpgrades(){
        upgradeList = new ArrayList<>();

        uFork = new Upgrade("fork", 0, upgrades.getJSONArray("fork"), images_upgrades, "upgradeIcons_attackFork");
        uScythe = new Upgrade("scythe", -1, upgrades.getJSONArray("scythe"), images_upgrades, "upgradeIcons_attackScythe");
        uDagger = new Upgrade("dagger", -1, upgrades.getJSONArray("dagger"), images_upgrades, "upgradeIcons_attackDagger");
        uSword = new Upgrade("sword", -1, upgrades.getJSONArray("sword"), images_upgrades, "upgradeIcons_attackSword");
        uBattleAxe = new Upgrade("battleAxe", -1, upgrades.getJSONArray("battleAxe"), images_upgrades, "upgradeIcons_attackBattleAxe");
        uMace = new Upgrade("mace", -1, upgrades.getJSONArray("mace"), images_upgrades, "upgradeIcons_attackMace");
        uBow = new Upgrade("bow", -1, upgrades.getJSONArray("bow"), images_upgrades, "upgradeIcons_attackBow");
        uCrossbow = new Upgrade("crossbow", -1, upgrades.getJSONArray("crossbow"), images_upgrades, "upgradeIcons_attackCrossbow");
        uCannon = new Upgrade("cannon", -1, upgrades.getJSONArray("cannon"), images_upgrades, "upgradeIcons_attackCannon");
        uBetterCannon = new Upgrade("betterCannon", -1, upgrades.getJSONArray("betterCannon"), images_upgrades, "upgradeIcons_attackBetterCannon");
        uCannonBall = new Upgrade("cannonBall", -1, upgrades.getJSONArray("cannonBall"), images_upgrades, "upgradeIcons_attackCannonBall");
        uSceptre = new Upgrade("sceptre", -1, upgrades.getJSONArray("sceptre"), images_upgrades, "upgradeIcons_attackSceptre");
        uBook = new Upgrade("book", -1, upgrades.getJSONArray("book"), images_upgrades, "upgradeIcons_attackBook");
        uGear = new Upgrade("gear", 0, upgrades.getJSONArray("gear"), images_upgrades, "upgradeIcons_attackGear");
        uSonar = new Upgrade("sonar", -1, upgrades.getJSONArray("sonar"), images_upgrades, "upgradeIcons_attackSonar");
        uHealth = new Upgrade("health", 0, upgrades.getJSONArray("health"), images_upgrades, "upgradeIcons_healthHealth");
        uBetterHealth = new Upgrade("betterHealth", -1, upgrades.getJSONArray("betterHealth"), images_upgrades, "upgradeIcons_healthBetterHealth");
        uBetterBetterHealth = new Upgrade("betterBetterHealth", -1, upgrades.getJSONArray("betterBetterHealth"), images_upgrades, "upgradeIcons_healthBetterBetterHealth");
        uRegeneration = new Upgrade("regeneration", -1, upgrades.getJSONArray("regeneration"), images_upgrades, "upgradeIcons_healthRegen");
        uShield = new Upgrade("shield", -1, upgrades.getJSONArray("shield"), images_upgrades, "upgradeIcons_healthShield");
        uGold = new Upgrade("gold", 0, upgrades.getJSONArray("gold"), images_upgrades, "upgradeIcons_incomeGold");
        uBetterGold = new Upgrade("betterGold", -1, upgrades.getJSONArray("betterGold"), images_upgrades, "upgradeIcons_incomeBetterGold");
        uDiamonds = new Upgrade("diamonds", -1, upgrades.getJSONArray("diamonds"), images_upgrades, "upgradeIcons_incomeDiamonds");
        uBetterDiamonds = new Upgrade("betterDiamonds", -1, upgrades.getJSONArray("betterDiamonds"), images_upgrades, "upgradeIcons_incomeBetterDiamonds");
        uDiscount10 = new Upgrade("discount10", -1, upgrades.getJSONArray("discount10"), images_upgrades, "upgradeIcons_incomeDiscountTen");
        uDiscount20 = new Upgrade("discount20", -1, upgrades.getJSONArray("discount20"), images_upgrades, "upgradeIcons_incomeDiscountTwenty");
        uDiscount30 = new Upgrade("discount30", -1, upgrades.getJSONArray("discount30"), images_upgrades, "upgradeIcons_incomeDiscountThirty");
        uUpgrade = new Upgrade("upgrade", -1, upgrades.getJSONArray("upgrade"), images_upgrades, "upgradeIcons_incomeUpgrade");
        uHammer = new Upgrade("hammer", -1, upgrades.getJSONArray("hammer"), images_upgrades, "upgradeIcons_incomeHammer");
        uBetterUpgrade = new Upgrade("betterUpgrade", -1, upgrades.getJSONArray("betterUpgrade"), images_upgrades, "upgradeIcons_incomeBetterUpgrade");
        uLuck = new Upgrade("luck", -1, upgrades.getJSONArray("luck"), images_upgrades, "upgradeIcons_incomeLuck");


        uFork.addNextUpgrade(uScythe,1);
        uScythe.addNextUpgrade(uDagger,1);
        uScythe.addNextUpgrade(uSceptre,1);
        uDagger.addNextUpgrade(uBow,1);
        uDagger.addNextUpgrade(uSword,2);
        uSword.addNextUpgrade(uBattleAxe,1);
        uBattleAxe.addNextUpgrade(uCannon,1);
        uBattleAxe.addNextUpgrade(uMace,1);
        uBow.addNextUpgrade(uCrossbow, uBow.getMaxLevel());
        uCannon.addNextUpgrade(uCannonBall,1);
        uCannonBall.addNextUpgrade(uBetterCannon,1);
        uSceptre.addNextUpgrade(uBook,1);
        uGear.addNextUpgrade(uSonar,1);
        uHealth.addNextUpgrade(uBetterHealth,1);
        uBetterHealth.addNextUpgrade(uRegeneration,1);
        uRegeneration.addNextUpgrade(uShield,1);
        uShield.addNextUpgrade(uBetterBetterHealth,1);
        uGold.addNextUpgrade(uBetterGold,1);
        uBetterGold.addNextUpgrade(uDiamonds,1);
        uBetterGold.addNextUpgrade(uDiscount10,1);
        uDiamonds.addNextUpgrade(uBetterDiamonds,1);
        uDiscount10.addNextUpgrade(uDiscount20,1);
        uDiscount10.addNextUpgrade(uUpgrade,1);
        uDiscount20.addNextUpgrade(uDiscount30,1);
        uUpgrade.addNextUpgrade(uHammer,1);
        uHammer.addNextUpgrade(uBetterUpgrade,1);
        uHammer.addNextUpgrade(uLuck,1);


        upgradeList.add(uFork);
        upgradeList.add(uScythe);
        upgradeList.add(uDagger);
        upgradeList.add(uSword);
        upgradeList.add(uBattleAxe);
        upgradeList.add(uMace);
        upgradeList.add(uBow);
        upgradeList.add(uCrossbow);
        upgradeList.add(uCannon);
        upgradeList.add(uBetterCannon);
        upgradeList.add(uCannonBall);
        upgradeList.add(uSceptre);
        upgradeList.add(uBook);
        upgradeList.add(uGear);
        upgradeList.add(uSonar);
        upgradeList.add(uHealth);
        upgradeList.add(uBetterHealth);
        upgradeList.add(uBetterBetterHealth);
        upgradeList.add(uRegeneration);
        upgradeList.add(uShield);
        upgradeList.add(uGold);
        upgradeList.add(uBetterGold);
        upgradeList.add(uDiamonds);
        upgradeList.add(uBetterDiamonds);
        upgradeList.add(uDiscount10);
        upgradeList.add(uDiscount20);
        upgradeList.add(uDiscount30);
        upgradeList.add(uUpgrade);
        upgradeList.add(uHammer);
        upgradeList.add(uBetterUpgrade);
        upgradeList.add(uLuck);

    }

    public void initListeners()
    {

        for (Upgrade u: upgradeList)
        {
            u.getImage().addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("kliklem na:" + u.getUpgradeName());
                    if (!u.isMaxLevel())
                    {
                        if (base.getDiamonds()>=u.getCostToUpgrade())
                        {
                            base.setPassiveUpgrade(u.getUpgrade());

                            u.levelUp();

                            unlockedUpgrades.put(u.getUpgradeName());

                            int j = u.getUnlocksLeft();
                            for (int i=0; i<j; i++)
                            {
                                if(u.getLevel() == u.getLevelToUnlock()){
                                    u.getNextUpgrade().unlock(images_upgrades);
                                    u.removeUnlocked();
                                }
                            }
                        }
                    }

                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    System.out.println("weszłem na:" + u.getUpgradeName());
                    base.setInfoToDisplay(4);
                    /*Label information;
                    information = u.returnInformation(textTooltipStyle.label);
                    tooltip = new TextTooltip("", textTooltipStyle);
                    tooltip.setActor(information);
                    tooltip.setInstant(true);
                    tooltip.enter(event, -72, y, pointer, fromActor);*/
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    System.out.println("wyszedłem");
                    base.setInfoToDisplay(4);
                    /*tooltip.exit(event, x, y, pointer, fromActor);*/
                }


            });

        }

    }

    private Upgrade findUpgrade(String upgradeName) {
        for (Upgrade u: upgradeList)
        {
            if (Objects.equals(u.getUpgradeName(), upgradeName))
                return u;
        }
        return null;
    }


    private void loadUpgrades() {

        for (int k=0; k < unlockedUpgrades.length(); k++) {
            Upgrade upgrade = findUpgrade(unlockedUpgrades.getString(k));
            base.setPassiveUpgrade(upgrade.getUpgrade());
            upgrade.levelUp();


            int j = upgrade.getUnlocksLeft();
            for (int i=0; i<j; i++)
            {
                if(upgrade.getLevel() == upgrade.getLevelToUnlock()){
                    upgrade.getNextUpgrade().unlock(images_upgrades);
                    upgrade.removeUnlocked();
                }
            }

        }


    }

}