package com.game.Entity;

import com.game.Screens.GameScreen;
import org.json.JSONObject;

import java.util.Iterator;

public class Base {
    private int maxHealth;
    private int health;
    private int armor;
    private int money;
    private int diamonds;
    private int wave;

    private int infoToDisplay;
    private String infoToDisplayName;
    private JSONObject infoToDisplayObjectNow, infoToDisplayObjectUpgraded;

    private String difficulty;
    private int cleanPrice;

    private JSONObject multiplayers;



    public enum State{
        Running, Paused, Resumed, GameOver
    }

    private State state;


    public Base(JSONObject actualGame){
        this.infoToDisplay = 0;
        this.infoToDisplayName = "";
        this.infoToDisplayObjectNow = null;
        this.infoToDisplayObjectUpgraded = null;

        state = State.Running;

        multiplayers = new JSONObject();
        multiplayers.put("damageMultiplayer",1f);
        multiplayers.put("attackSpeedMultiplayer",1f);
        multiplayers.put("moreProjectilesMultiplayer",1f);
        multiplayers.put("rangeMultiplayer",1f);
        multiplayers.put("bonusHealth",1f);
        multiplayers.put("healthRegeneration",1f);
        multiplayers.put("reductionMultiplayer",1f);
        multiplayers.put("goldMultiplayer",1f);
        multiplayers.put("diamondsMultiplayer",1f);
        multiplayers.put("discountMultiplayer",1f);
        multiplayers.put("costMultiplayer",1f);
        multiplayers.put("luckMultiplayer",1f);

        this.money = actualGame.getInt("gold");
        this.diamonds = actualGame.getInt("diamonds");
        this.maxHealth = 100;
        this.health = 100;
        this.armor = 100;
        this.difficulty = actualGame.getString("difficulty");
        this.wave = 0;
        this.cleanPrice = 200;
    }

    public Base(int maxHealth, int health, int armor){
        this.money = 0;
        this.maxHealth = maxHealth;
        this.health = health;
        this.armor = armor;

    }


    public void setPassiveUpgrade(JSONObject upgrade) {
        this.diamonds -= upgrade.getInt("cost");

        String multiplayer = String.valueOf(upgrade.keySet().toArray()[upgrade.keySet().size() -1]);
        multiplayers.put(multiplayer, multiplayers.getFloat(multiplayer)+upgrade.getFloat(multiplayer));

        System.out.println(multiplayers.getFloat(multiplayer));
    }




    public int getInfoToDisplay() {
        return infoToDisplay;
    }

    public String getInfoToDisplayName() {
        return infoToDisplayName;
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setInfoToDisplay(int infoToDisplay) {
        this.infoToDisplay = infoToDisplay;
    }

    public void setInfoToDisplay(int infoToDisplay, String name, JSONObject towerNow, JSONObject towerUpgrade) {
        this.infoToDisplay = infoToDisplay;
        infoToDisplayName = name;
        infoToDisplayObjectNow = towerNow;
        infoToDisplayObjectUpgraded = towerUpgrade;
    }

    public void setInfoToDisplay(int infoToDisplay, String name) {
        this.infoToDisplay = infoToDisplay;
        infoToDisplayName = name;
    }

    public JSONObject getInfoToDisplayObjectNow() {
        return infoToDisplayObjectNow;
    }

    public JSONObject getInfoToDisplayObjectUpgraded() {
        return infoToDisplayObjectUpgraded;
    }

    public void damageBase(int dmg){
        this.health = this.health - dmg;
    }


    public void healBase(int heal){
        this.health = this.health + heal;
    }




    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void increaseMoney(int money) {
        this.money += money;
    }

    public void decreaseMoney(int money) {
        this.money -= money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public void increaseDiamonds(int diamonds) {
        this.diamonds += diamonds;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }
    public String getDifficulty() {
        return difficulty;
    }

    public void increaseWave(int wave) {
        this.wave += wave;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getCleanPrice() {
        return cleanPrice;
    }
}
