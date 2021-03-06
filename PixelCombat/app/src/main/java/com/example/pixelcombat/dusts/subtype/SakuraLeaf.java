package com.example.pixelcombat.dusts.subtype;

import com.example.pixelcombat.animation.util.impl.ImageMover;
import com.example.pixelcombat.dusts.Dust;
import com.example.pixelcombat.enums.ScreenProperty;
import com.example.pixelcombat.math.Vector2d;
import com.example.pixelcombat.utils.LocatedBitmap;

import java.util.ArrayList;

public class SakuraLeaf extends Dust {

    private ImageMover dustMover;
    private float scaleFactor;

    public SakuraLeaf(ArrayList<LocatedBitmap> images, ArrayList<Float> times, Vector2d pos, boolean isRight, float VX, float VY, float scaleFactor) {
        super(images, times, true, pos, isRight);
        this.scaleFactor = scaleFactor;
        dustMover = new ImageMover(VX, VY);
    }

    @Override
    public void update() {
        super.update();
        dustMover.move(this);
    }


    @Override
    public boolean isFinished() {
        return this.getPos().y >= ScreenProperty.SCREEN_HEIGHT;
    }

    @Override
    public float getScaleFactor() {
        return scaleFactor;
    }
}
