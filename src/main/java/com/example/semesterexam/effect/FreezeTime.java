package com.example.semesterexam.effect;

import com.example.semesterexam.core.Character;
import com.example.semesterexam.core.Effect;
import com.example.semesterexam.manage.GameScreen;

import java.io.IOException;

public class FreezeTime extends Effect {
    public FreezeTime(GameScreen gameScreen, Character character, long timeExist) throws IOException {
        super(gameScreen, character, timeExist);
    }

    @Override
    public void setDefaultAction() {
        addActions("Default", gameScreen.getAction("EffectPack:FreezeTime"));
        setActions("Default");
    }
}
