package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Main;
import com.game.Manager.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileCloudScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private FileReader fileReader;
    private ConnectionManager connectionManager;
    private LanguageManager languageManager;
    private ButtonStyleManager buttonStyleManager;
    private TextFieldStyleManager textFieldStyleManager;

    private List<User_save> profiles;
    private BitmapFont font;
    private TextureAtlas taButtonsDefault, taProfileBanner, taEmptyTextfield, taButtonsProfile;
    private Skin images_default, images_empty, image_profiles, images_settings;

    private TextButton bBack, bPlay, bOtherScreen, bNewProfile01, bNewProfile02, bNewProfile03, bDialogCancel, bDialogCreate, cDialogNormalDifficulty, cDialogHardDifficulty;
    private Table table_profile_01, table_profile_02, table_profile_03, table_default, table_previous, table_Dialog;
    private TextField tDifficulty01, tDifficulty02,tDifficulty03,
            tFinishedMaps01, tFinishedMaps02, tFinishedMaps03,
            tWave01, tWave02, tWave03,
            tGold01, tGold02, tGold03,
            tDiamonds01, tDiamonds02, tDiamonds03,
            tDialogNormalDifficulty, tDialogHardDifficulty;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bBack,textButtonStyle_bSave, textButtonStyle_bPrevious, textButtonStyle_bNewProfile, textButtonStyle_cDialogDifficultyChecked, textButtonStyle_cDialogDifficultyUnchecked;
    private TextField.TextFieldStyle textFieldStyle;
    private Music backgroundMusic;

    private Dialog newGameDialog;
    private String chosenDifficulty = null;
    private int chosenProfileToCreate;


    public ProfileCloudScreen(Main game){
        this.game = game;
        stage = new Stage();
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){
            languageManager = new LanguageManager(fileReader.getLanguageValue());
        }else{
            languageManager = new LanguageManager("English");
        }


        initProfileCloudUI();
        profiles = new ArrayList<>();
        buttonStyleManager = new ButtonStyleManager();
        textFieldStyleManager = new TextFieldStyleManager();
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bBack, images_default, font, "defaultButton", "defaultButton");
        bBack = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bBack"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bSave, images_default, font, "defaultButton", "defaultButton");
        bPlay = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bPlay"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSave));

        bDialogCreate = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bDialogCreate"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSave));
        bDialogCancel = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bDialogCancel"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSave));

        //TODO Dialog Seed input

        buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogDifficultyChecked, images_settings, font, "checkbox_on", "checkbox_on");
        buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked, images_settings, font, "checkbox_off", "checkbox_off");

        cDialogNormalDifficulty = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked));
        cDialogHardDifficulty = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked));

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bPrevious, image_profiles, font, "previous_screen_button", "previous_screen_button");
        bOtherScreen = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_bPrevious));


        buttonStyleManager.setTextButtonStyle(textButtonStyle_bNewProfile, image_profiles, font, "new_profile_button_up", "new_profile_button_down");
        bNewProfile01 = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        bNewProfile02 = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        bNewProfile03 = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        textFieldStyleManager.setTextFieldStyle(textFieldStyle, images_empty, font, "empty_background", Color.WHITE);





        background = new Texture("background.png");
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Texture bg = new Texture(new FileHandle("assets/profile_banner.png"));
        Texture dialogBg = new Texture(new FileHandle("assets/dialog/skin_dialog.png"));
        Texture icon = new Texture(new FileHandle("assets/icons/local.png"));
        Image local01 = new Image(icon);
        Image local02 = new Image(icon);
        Image local03 = new Image(icon);
        table_profile_01.setBounds(Gdx.graphics.getWidth()/10*2, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_01.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        table_profile_02.setBounds(Gdx.graphics.getWidth()/10*4, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_02.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        table_profile_03.setBounds(Gdx.graphics.getWidth()/10*6, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_03.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));


        JSONObject loadSaves = new JSONObject();

        loadSaves.put("login", game.getLogin());

        JSONObject loadResponse = connectionManager.requestSend(loadSaves, "http://localhost:9000/api/downloadSaves");
        System.out.println(loadResponse.getInt("status"));
        if (loadResponse.getInt("status") == 200)
        {
            int numberOfLoadedSaves = loadResponse.getJSONArray("loadedData").length();
            JSONObject save;
            for (int i =0; i<numberOfLoadedSaves; i++)
            {
                save =  loadResponse.getJSONArray("loadedData").getJSONObject(i);

                if( save.getInt("profileNumber")==1)
                {
                    User_save user_save_01 = new User_save();
                    user_save_01.seed = save.getInt("seed");
                    user_save_01.difficulty = save.getString("difficulty");
                    user_save_01.finishedMaps = save.getInt("finishedMaps");
                    user_save_01.wave = save.getInt("wave");
                    user_save_01.gold = save.getInt("gold");
                    user_save_01.diamonds = save.getInt("diamonds");

                    profiles.add(user_save_01);
                    tDifficulty01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "difficulty_field") + user_save_01.difficulty, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tDifficulty01.setAlignment(Align.left);
                    tFinishedMaps01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "finishedmaps_field") + user_save_01.finishedMaps, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tFinishedMaps01.setAlignment(Align.left);
                    tWave01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "wave_field") + user_save_01.wave, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tWave01.setAlignment(Align.left);
                    tGold01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "gold_field") + user_save_01.gold, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tGold01.setAlignment(Align.left);
                    tDiamonds01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "diamonds_field") + user_save_01.diamonds, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tDiamonds01.setAlignment(Align.left);
                    table_profile_01.add(local01).width(table_profile_01.getHeight()/20).height(table_profile_01.getHeight()/20).align(Align.right);
                    table_profile_01.row();
                    table_profile_01.add(tDifficulty01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10);
                    table_profile_01.row();
                    table_profile_01.add(tFinishedMaps01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10);
                    table_profile_01.row();
                    table_profile_01.add(tWave01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10);
                    table_profile_01.row();
                    table_profile_01.add(tGold01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10);
                    table_profile_01.row();
                    table_profile_01.add(tDiamonds01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10).padBottom(table_profile_01.getHeight()/2-table_profile_01.getHeight()/10);
                    table_profile_01.debug();
                    table_profile_01.setTouchable(Touchable.enabled);
                    table_profile_01.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            //wyłapuje tylko na textfieldach, a nie na całym table_profile
                        }
                    });

                }
                else if( save.getInt("profileNumber")==2)
                {
                    User_save user_save_02 = new User_save();
                    user_save_02.seed = save.getInt("seed");
                    user_save_02.difficulty = save.getString("difficulty");
                    user_save_02.finishedMaps = save.getInt("finishedMaps");
                    user_save_02.wave = save.getInt("wave");
                    user_save_02.gold = save.getInt("gold");
                    user_save_02.diamonds = save.getInt("diamonds");
                    profiles.add(user_save_02);
                    tDifficulty02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "difficulty_field") + user_save_02.difficulty, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tDifficulty02.setAlignment(Align.left);
                    tFinishedMaps02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "finishedmaps_field") + user_save_02.finishedMaps, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tFinishedMaps02.setAlignment(Align.left);
                    tWave02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "wave_field") + user_save_02.wave, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tWave02.setAlignment(Align.left);
                    tGold02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "gold_field") + user_save_02.gold, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tGold02.setAlignment(Align.left);
                    tDiamonds02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "diamonds_field") + user_save_02.diamonds, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tDiamonds02.setAlignment(Align.left);
                    table_profile_02.add(local02).width(table_profile_02.getHeight()/20).height(table_profile_02.getHeight()/20).align(Align.right);
                    table_profile_02.row();
                    table_profile_02.add(tDifficulty02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10);
                    table_profile_02.row();
                    table_profile_02.add(tFinishedMaps02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10);
                    table_profile_02.row();
                    table_profile_02.add(tWave02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10);
                    table_profile_02.row();
                    table_profile_02.add(tGold02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10);
                    table_profile_02.row();
                    table_profile_02.add(tDiamonds02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10).padBottom(table_profile_02.getHeight()/2-table_profile_02.getHeight()/10);
                    table_profile_02.debug();
                    table_profile_02.setTouchable(Touchable.enabled);
                    table_profile_02.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            //wyłapuje tylko na textfieldach, a nie na całym table_profile
                        }
                    });

                }
                else if( save.getInt("profileNumber")==3)
                {
                    User_save user_save_03 = new User_save();
                    user_save_03.seed = save.getInt("seed");
                    user_save_03.difficulty = save.getString("difficulty");
                    user_save_03.finishedMaps = save.getInt("finishedMaps");
                    user_save_03.wave = save.getInt("wave");
                    user_save_03.gold = save.getInt("gold");
                    user_save_03.diamonds = save.getInt("diamonds");
                    profiles.add(user_save_03);
                    tDifficulty03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "difficulty_field") + user_save_03.difficulty, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tDifficulty03.setAlignment(Align.left);
                    tFinishedMaps03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "finishedmaps_field") + user_save_03.finishedMaps, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tFinishedMaps03.setAlignment(Align.left);
                    tWave03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "wave_field") + user_save_03.wave, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tWave03.setAlignment(Align.left);
                    tGold03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "gold_field") + user_save_03.gold, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tGold03.setAlignment(Align.left);
                    tDiamonds03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "diamonds_field") + user_save_03.diamonds, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                    tDiamonds03.setAlignment(Align.left);
                    table_profile_03.add(local03).width(table_profile_03.getHeight()/20).height(table_profile_03.getHeight()/20).align(Align.right);
                    table_profile_03.row();
                    table_profile_03.add(tDifficulty03).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10);
                    table_profile_03.row();
                    table_profile_03.add(tFinishedMaps03).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10);
                    table_profile_03.row();
                    table_profile_03.add(tWave03).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10);
                    table_profile_03.row();
                    table_profile_03.add(tGold03).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10);
                    table_profile_03.row();
                    table_profile_03.add(tDiamonds03).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10).padBottom(table_profile_03.getHeight()/2-table_profile_03.getHeight()/10);
                    table_profile_03.debug();
                    table_profile_03.setTouchable(Touchable.enabled);
                    table_profile_03.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            //wyłapuje tylko na textfieldach, a nie na całym table_profile
                        }
                    });

                }




            }//for




            }//if_downloaded


        //no saves -> +
        if (table_profile_01.getChildren().size==0) {
            table_profile_01.add(bNewProfile01).height(table_profile_01.getHeight()/4).width(table_profile_01.getHeight()/4).padBottom(table_profile_01.getHeight()/4);
            table_profile_01.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfileToCreate = 1;
                    newGameDialog.show(stage);
                }
            });
        }
        if (table_profile_02.getChildren().size==0) {
            table_profile_02.add(bNewProfile02).height(table_profile_02.getHeight() / 4).width(table_profile_02.getHeight() / 4).padBottom(table_profile_02.getHeight() / 4);
            table_profile_02.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfileToCreate = 2;
                    newGameDialog.show(stage);
                }
            });
        }
        if (table_profile_03.getChildren().size==0) {
            table_profile_03.add(bNewProfile03).height(table_profile_03.getHeight()/4).width(table_profile_03.getHeight()/4).padBottom(table_profile_03.getHeight()/4);
            table_profile_03.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfileToCreate = 3;
                    newGameDialog.show(stage);
                }
            });
        }


        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MenuScreen(game));
                    dispose();
                    return true;
                }

                if (keycode == Input.Keys.S) {
                    JSONObject saveData = new JSONObject();
                    saveData.put("login", game.getLogin());
                    saveData.put("profileNumber", 1);
                    saveData.put("seed", 123);
                    saveData.put("difficulty", "normal");
                    saveData.put("finishedMaps", 123);
                    saveData.put("wave", 10);
                    saveData.put("gold", 12233);
                    saveData.put("diamonds", 0);

                    JSONObject saveResponse = connectionManager.requestSend(saveData, "http://localhost:9000/api/uploadSave");
                    System.out.println(saveResponse);

                    return true;
                }


                if (keycode == Input.Keys.D) {
                    JSONObject saveData = new JSONObject();
                    saveData.put("login", game.getLogin());
                    saveData.put("profileNumber", 1);
                    saveData.put("seed", 123);
                    saveData.put("difficulty", "normal");
                    saveData.put("finishedMaps", 123);
                    saveData.put("wave", 10);
                    saveData.put("gold", 12233);
                    saveData.put("diamonds", 1111);

                    JSONObject saveResponse = connectionManager.requestSend(saveData, "http://localhost:9000/api/uploadSave");
                    System.out.println(saveResponse);

                    return true;
                }

                if (keycode == Input.Keys.F) {
                    JSONObject deleteData = new JSONObject();
                    deleteData.put("login", game.getLogin());
                    deleteData.put("profileNumber", 1);


                    JSONObject deleteResponse = connectionManager.requestSend(deleteData, "http://localhost:9000/api/deleteSave");
                    System.out.println(deleteResponse);

                    return true;
                }

                return super.keyDown(event, keycode);
            }
        });




        //to calibrate
        table_previous.setBounds((Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/10*9 - Gdx.graphics.getHeight()/10), Gdx.graphics.getWidth()/10*2,Gdx.graphics.getHeight()/10, Gdx.graphics.getWidth()/10*2);

        table_previous.add(bOtherScreen);
        table_previous.debug();
        stage.addActor(table_previous);
        bOtherScreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ProfileLocalScreen(game));
            }
        });





        newGameDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(dialogBg)))) {
            public void result(Object obj) {
                newGameDialog.cancel();
            }
        };

        tDialogNormalDifficulty  = new TextField(languageManager.getValue(languageManager.getLanguage(), "tNormalDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogHardDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "tHardDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));




        table_Dialog.setWidth(350);
        table_Dialog.setX(200);
        table_Dialog.setY(300);
        table_Dialog.add(cDialogNormalDifficulty);
        table_Dialog.add(tDialogNormalDifficulty);
        table_Dialog.row();
        table_Dialog.add(cDialogHardDifficulty);
        table_Dialog.add(tDialogHardDifficulty);
        newGameDialog.addActor(table_Dialog);

        newGameDialog.button(bDialogCancel);
        newGameDialog.button(bDialogCreate);

        cDialogNormalDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogNormalDifficulty.setStyle(textButtonStyle_cDialogDifficultyChecked);
                cDialogHardDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                chosenDifficulty = "normal";
            }
        });

        cDialogHardDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogNormalDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                cDialogHardDifficulty.setStyle(textButtonStyle_cDialogDifficultyChecked);
                chosenDifficulty = "hard";
            }
        });


        bDialogCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                newGameDialog.hide();
            }
        });

        bDialogCreate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (chosenDifficulty !=null)
                {
                    System.out.println("Stworzono gre na profilu " +chosenProfileToCreate + "o poziomie trudnosci " + chosenDifficulty);
                }
            }
        });









        table_default.setBounds(Gdx.graphics.getWidth()/10*3, Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/10*4,50);

        table_default.add(bBack).padRight(200);
        table_default.add(bPlay).padLeft(90);


        stage.addActor(table_profile_01);
        stage.addActor(table_profile_02);
        stage.addActor(table_profile_03);
        stage.addActor(table_default);


        bBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0,0);
        game.batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void initProfileCloudUI() {
        stage = new Stage();
        background = new Texture("background.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        if(Gdx.graphics.getWidth() < 1281){
            parameter.size = 12;
        }else{
            parameter.size = 15;
        }
        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);
        taButtonsDefault = new TextureAtlas("assets/buttons/buttons_default.pack");
        taEmptyTextfield = new TextureAtlas("assets/buttons/buttons_settings.pack");
        taButtonsProfile = new TextureAtlas("assets/buttons/buttons_profile.pack");

        connectionManager = new ConnectionManager();

        images_default = new Skin(taButtonsDefault);
        images_empty = new Skin(taEmptyTextfield);
        image_profiles = new Skin(taButtonsProfile);
        images_settings = new Skin(new TextureAtlas("assets/buttons/buttons_settings.pack"));
        table_default = new Table(images_default);
        table_previous = new Table(images_default);
        table_Dialog = new Table(images_settings);
        table_profile_01 = new Table();
        table_profile_02 = new Table();
        table_profile_03 = new Table();

        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textButtonStyle_bPrevious = new TextButton.TextButtonStyle();
        textButtonStyle_bNewProfile = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
        backgroundMusic = game.getMusic();
        textButtonStyle_cDialogDifficultyChecked = new TextButton.TextButtonStyle();
        textButtonStyle_cDialogDifficultyUnchecked  = new TextButton.TextButtonStyle();




    }
}
