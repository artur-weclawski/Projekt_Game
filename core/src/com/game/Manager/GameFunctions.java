package com.game.Manager;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.game.Screens.GameScreen;

public class GameFunctions {

    public static Image[][] getEmptyBuildingsArr(){
        Image[][] arr = new Image[10][15];
        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                arr[i][j] = new Image(images_buildings,"empty");
                arr[i][j].setTouchable(Touchable.disabled);

            }
        }


        return arr;
    }


    public static Image[][] addBuilding(GameScreen gameScreen, Image[][] buildingsArr, int x, int y, String tileName){
        Skin buildings_skin = new Skin(new TextureAtlas("assets/icons/buildings.pack"));
        buildingsArr[y][x].setDrawable(buildings_skin, tileName);
        buildingsArr[y][x].setName(tileName);
        buildingsArr[y][x].setTouchable(Touchable.enabled);


        buildingsArr[y][x].addListener(new ImageClickListener(x,y,buildingsArr[y][x].getName()){
            public void clicked(InputEvent event, float x, float y) {
                this.setLastClickedTile(gameScreen.lastClickedMapTile);
                gameScreen.mouseClickBuildingTile();
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                this.setLastClickedTile(gameScreen.lastClickedMapTile);
                //gameScreen.mouseEnterMapTile();

            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //this.setLastClickedTile();
                //gameScreen.mouseExitMapTile();
            }

        });


        return buildingsArr;
    }

    public static Image[][] sellBuilding(Image[][] buildingsArr, int x, int y){

        Skin buildings_skin = new Skin(new TextureAtlas("assets/icons/buildings.pack"));
        buildingsArr[y][x].setDrawable(buildings_skin, "empty");
        buildingsArr[y][x].setName("empty");
        buildingsArr[y][x].setTouchable(Touchable.disabled);

        buildingsArr[y][x].clearListeners();


        return buildingsArr;
    }

    public static Table getBuildingsTable(Image[][] arr) {
        Table t = new Table();
        t.setBounds(500, 200, 64*15, 64*10);

        for (int i = 0; i<10; i++)
        {
            for (int j = 0; j<15; j++)
            {
                t.add(arr[i][j]);
            }
            t.row();
        }

        return t;
    }





    public static Image[][] getOperationsArr(GameScreen gameScreen){
        Image[][] arr = new Image[4][2];
        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));


        arr[0][0] = new Image(images_buildings, "sword");
        arr[0][1] = new Image(images_buildings, "bow");
        arr[1][0] = new Image(images_buildings, "mage");
        arr[1][1] = new Image(images_buildings, "cannon");
        arr[2][0] = new Image(images_buildings, "clean");
        arr[2][1] = new Image(images_buildings, "sell");
        arr[3][0] = new Image(images_buildings, "stickyRoad");
        arr[3][1] = new Image(images_buildings, "roadNeedles");

        arr[0][0].setName("sword");
        arr[0][1].setName("bow");
        arr[1][0].setName("mage");
        arr[1][1].setName("cannon");
        arr[2][0].setName("clean");
        arr[2][1].setName("sell");
        arr[3][0].setName("stickyRoad");
        arr[3][1].setName("roadNeedles");

        for(int i = 0; i<4; i++)
            for(int j = 0; j<2; j++) {
                arr[i][j].addListener(new ImageClickListener(j, i, arr[i][j].getName()) {
                    public void clicked(InputEvent event, float x, float y) {
                        this.setLastClickedTile(gameScreen.lastClickedOperationTile);
                        gameScreen.mouseClickOperation();
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        this.setLastClickedTile(gameScreen.lastClickedOperationTile);
                        gameScreen.mouseEnterOperation();

                    }

                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        //this.lastClickedBuilding();
                        gameScreen.mouseExitOperation();
                    }


                });
            }


        return arr;
    }


    public static Image[][] getOperationsSelectedArr() {
        Image[][] arr = new Image[4][2];
        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));


        arr[0][0] = new Image(images_buildings, "sword");
        arr[0][1] = new Image(images_buildings, "bow");
        arr[1][0] = new Image(images_buildings, "mage");
        arr[1][1] = new Image(images_buildings, "cannon");
        arr[2][0] = new Image(images_buildings, "clean");
        arr[2][1] = new Image(images_buildings, "sell");
        arr[3][0] = new Image(images_buildings, "stickyRoad");
        arr[3][1] = new Image(images_buildings, "roadNeedles");

        arr[0][0].setName("sword");
        arr[0][1].setName("bow");
        arr[1][0].setName("mage");
        arr[1][1].setName("cannon");
        arr[2][0].setName("clean");
        arr[2][1].setName("sell");
        arr[3][0].setName("stickyRoad");
        arr[3][1].setName("roadNeedles");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                arr[i][j] = new Image(images_buildings, "empty");
                arr[i][j].setTouchable(Touchable.disabled);
            }
        }


        return arr;
    }


    public static Table getOperationsTable(Image[][] arr)
    {
        Table t = new Table();
        t.setBounds(100, 400, 64*2, 64*4);

        for (int i = 0; i<4; i++)
        {
            for (int j = 0; j<2; j++)
            {
                t.add(arr[i][j]);
            }

            t.row();

        }

        return t;
    }




}

