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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.Main;
import com.game.Manager.*;
import com.game.State.GameState;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProfileLocalScreen implements Screen {
    private Main game;
    private FileReader fileReader;
    private ButtonStyleManager buttonStyleManager;

    private TextFieldStyleManager textFieldStyleManager;
    private List<User_save> profiles;
    private Stage stage;
    private Texture background;
    private BitmapFont font, font_profile;
    private TextureAtlas taButtonsDefault, taEmptyTextfield, taButtonsProfile, taDialog;
    private Skin images_default, images_empty, image_profiles, images_settings; //<- to delete
    private TextButton bBack, bPlay, bOtherScreen, bNewProfile01, bNewProfile02, bNewProfile03, bDialogCancel, bDialogCreate, cDialogEasyDifficulty, cDialogNormalDifficulty, cDialogHardDifficulty;
    private Table table_profile_01, table_profile_02, table_profile_03, table_default, table_next, table_Dialog;
    private TextField tDialogEasyDifficulty, tDialogNormalDifficulty, tDialogHardDifficulty;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bBack,textButtonStyle_bSave, textButtonStyle_bNext, textButtonStyle_bNewProfile, textButtonStyle_cDialogDifficultyChecked, textButtonStyle_cDialogDifficultyUnchecked;
    private TextField.TextFieldStyle textFieldStyle;
    private Music backgroundMusic;
    private LanguageManager languageManager;
    private ConnectionManager connectionManager;
    private JSONObject save1, save2, save3;

    private JSONObject loadResponse;
    private Dialog newGameDialog;
    private String chosenDifficulty = null;
    private int chosenProfile;

    private boolean isDialog = false;//<- to delete or leave
    private Image attack01, attack02, attack03, attack04, attack05, health01, health02, health03, income01, income02;
    public ProfileLocalScreen(Main game){
        this.game = game;
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){
            languageManager = new LanguageManager(fileReader.getLanguageValue());
        }else{
            languageManager = new LanguageManager("English");
        }
        initProfileLocalUI();
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

        cDialogEasyDifficulty = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked));
        cDialogNormalDifficulty = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked));
        cDialogHardDifficulty = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked));

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bNext, image_profiles, font, "next_screen_button", "next_screen_button");
        bOtherScreen = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNext));

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bNewProfile, image_profiles, font, "new_profile_button_up", "new_profile_button_down");
        bNewProfile01 = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        bNewProfile02 = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        bNewProfile03 = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        textFieldStyleManager.setTextFieldStyle(textFieldStyle, images_empty, font, "empty_background", Color.WHITE);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        if(game.getIsLogged()){
            table_next.setBounds(Gdx.graphics.getWidth()/10*9, Gdx.graphics.getWidth()/10*2,Gdx.graphics.getHeight()/10, Gdx.graphics.getWidth()/10*2);
            table_next.add(bOtherScreen);

            JSONObject loadSaves = new JSONObject().put("login", game.getLogin());

            new Thread(() -> {
                loadResponse = connectionManager.requestSend(loadSaves, "api/downloadSaves");

                System.out.println(loadResponse.getInt("status"));

                if (loadResponse.getInt("status") == 200 || loadResponse.getInt("status") == 201) {
                    stage.addActor(table_next);
                } else {
                    System.out.println(languageManager.getValue(languageManager.getLanguage(), loadResponse.getString("message")));
                }
            }).start();

            /*
            try {
                new Thread(() -> {
                    loadResponse = connectionManager.requestSend(loadSaves, "api/downloadSaves");

                    System.out.println(loadResponse.getInt("status"));

                    if (loadResponse.getInt("status") == 200 || loadResponse.getInt("status") == 201)
                    {
                        stage.addActor(table_next);
                    }
                    else {
                        System.out.println(languageManager.getValue(languageManager.getLanguage(), loadResponse.getString("message")));
                    }


                }){{start();}}.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            */

            bOtherScreen.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    game.setScreen(new ProfileCloudScreen(game, loadResponse));

                }
            });
        }




        Texture bg = new Texture(new FileHandle("assets/profile_banner.png"));
        Texture dialogBg = new Texture(new FileHandle("assets/dialog/skin_dialog.png"));
        Texture icon = new Texture(new FileHandle("assets/icons/local.png"));
        Image local01 = new Image(icon);
        Image local02 = new Image(icon);
        Image local03 = new Image(icon);

        Texture iconDelete = new Texture(new FileHandle("assets/icons/delete.png"));
        Image delete01 = new Image(icon);
        Image delete02 = new Image(icon);
        Image delete03 = new Image(icon);

        newGameDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(dialogBg)))) {
            public void result(Object obj) {
                newGameDialog.cancel();
            }
        };
        tDialogEasyDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "tEasyDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogNormalDifficulty  = new TextField(languageManager.getValue(languageManager.getLanguage(), "tNormalDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogHardDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "tHardDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));




        table_Dialog.setWidth(350);
        table_Dialog.setX(200);
        table_Dialog.setY(300);
        table_Dialog.add(cDialogEasyDifficulty);
        table_Dialog.add(tDialogEasyDifficulty);
        table_Dialog.row();
        table_Dialog.add(cDialogNormalDifficulty);
        table_Dialog.add(tDialogNormalDifficulty);
        table_Dialog.row();
        table_Dialog.add(cDialogHardDifficulty);
        table_Dialog.add(tDialogHardDifficulty);
        newGameDialog.addActor(table_Dialog);

        newGameDialog.button(bDialogCancel);
        newGameDialog.button(bDialogCreate);

        cDialogEasyDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogEasyDifficulty.setStyle(textButtonStyle_cDialogDifficultyChecked);
                cDialogNormalDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                cDialogHardDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                chosenDifficulty = "easy";
            }
        });

        cDialogNormalDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogEasyDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                cDialogNormalDifficulty.setStyle(textButtonStyle_cDialogDifficultyChecked);
                cDialogHardDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                chosenDifficulty = "normal";
            }
        });

        cDialogHardDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogEasyDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
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
                    System.out.println("Stworzono gre na profilu " + chosenProfile + "o poziomie trudnosci " + chosenDifficulty);
                    GameState.setGameState(GameState.PLAYING);
                    ///in cloud put profileNumber
                    game.setScreen(new GameScreen(game, ProfileManager.createEmptySave(chosenDifficulty, chosenProfile), true));
                }
            }
        });


        //


        table_profile_01.setBounds(Gdx.graphics.getWidth()/10*2, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_01.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        table_profile_02.setBounds(Gdx.graphics.getWidth()/10*4, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_02.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        table_profile_03.setBounds(Gdx.graphics.getWidth()/10*6, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_03.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));

        table_default.setBounds(Gdx.graphics.getWidth()/10*3, Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/10*4,50);

        if(fileReader.fileExists("save/save01l.json")){
            save1 = fileReader.downloadSaveAsJSONObject("save/save01l.json");
            save1.put("profileNumber",1);
            table_profile_01 = ProfileManager.createProfileTable(save1, font_profile, languageManager, Gdx.graphics.getWidth()/10*2, "assets/icons/local.png");
            table_profile_01.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("zostałem wybrany");
                    //wyłapuje tylko na textfieldach, a nie na całym table_profile
                    GameState.setGameState(GameState.PLAYING);
                    game.setScreen(new GameScreen(game, save1, true));

                }
            });
        }else{
            table_profile_01 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*2);
            table_profile_01.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfile = 1;
                    newGameDialog.show(stage);
                }
            });
        }

        if(fileReader.fileExists("save/save02l.json")){
            save2 = fileReader.downloadSaveAsJSONObject("save/save02l.json");
            save2.put("profileNumber",2);
            table_profile_02 = ProfileManager.createProfileTable(save2, font_profile, languageManager, Gdx.graphics.getWidth()/10*4, "assets/icons/local.png");
            table_profile_02.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("zostałem wybrany");
                    //wyłapuje tylko na textfieldach, a nie na całym table_profile
                    GameState.setGameState(GameState.PLAYING);
                    game.setScreen(new GameScreen(game, save2, true));

                }
            });
        }else{
            table_profile_02 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*4);
            table_profile_02.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfile = 2;
                    newGameDialog.show(stage);
                }
            });
        }

        if(fileReader.fileExists("save/save03l.json")){
            save3 = fileReader.downloadSaveAsJSONObject("save/save03l.json");
            save3.put("profileNumber",3);
            table_profile_03 = ProfileManager.createProfileTable(save3, font_profile, languageManager, Gdx.graphics.getWidth()/10*6, "assets/icons/local.png");
            table_profile_03.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameState.setGameState(GameState.PLAYING);
                    game.setScreen(new GameScreen(game, save3, true));
                }
            });
        }else{
            table_profile_03 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*6);
            table_profile_03.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfile = 3;
                    newGameDialog.show(stage);
                }
            });

        }

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

        //temp?
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MenuScreen(game));
                    dispose();
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        if(isDialog){
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            table_profile_01.setVisible(false);
            table_profile_02.setVisible(false);
            table_profile_03.setVisible(false);
            table_default.setVisible(false);
            table_next.setVisible(false);
        }else{
            game.batch.draw(background, 0,0);
            table_profile_01.setVisible(true);
            table_profile_02.setVisible(true);
            table_profile_03.setVisible(true);
            table_default.setVisible(true);
            table_next.setVisible(true);
        }
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

    private void initProfileLocalUI(){
        stage = new Stage();
        background = new Texture("background.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 13;

        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);
        if(Gdx.graphics.getWidth() < 1281){
            parameter.size = 9;
        }
        else if(Gdx.graphics.getWidth() < 1601){
            parameter.size = 11;
        }else{
            parameter.size = 13;
        }
        font_profile = new BitmapFont();
        font_profile = generator.generateFont(parameter);
        taButtonsDefault = new TextureAtlas("assets/buttons/buttons_default.pack");
        //TODO dodaj jeden więcej przycisk >
        taEmptyTextfield = new TextureAtlas("assets/buttons/buttons_settings.pack");
        taButtonsProfile = new TextureAtlas("assets/buttons/buttons_profile.pack");
        taDialog = new TextureAtlas("assets/dialog/skin_dialog.pack");//<- to delete
        connectionManager = new ConnectionManager();
        images_default = new Skin(taButtonsDefault);
        images_empty = new Skin(taEmptyTextfield);
        image_profiles = new Skin(taButtonsProfile);
        images_settings = new Skin(new TextureAtlas("assets/buttons/buttons_settings.pack"));
        table_default = new Table(images_default);
        table_next = new Table(images_default);
        table_Dialog = new Table(images_settings);
        table_profile_01 = new Table();
        table_profile_02 = new Table();
        table_profile_03 = new Table();
        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textButtonStyle_bNext = new TextButton.TextButtonStyle();
        textButtonStyle_bNewProfile = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
        backgroundMusic = game.getMusic();
        textButtonStyle_cDialogDifficultyChecked = new TextButton.TextButtonStyle();
        textButtonStyle_cDialogDifficultyUnchecked  = new TextButton.TextButtonStyle();

    }
}
