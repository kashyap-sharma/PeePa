package com.jlabs.peepaid.anima;



/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {

            case PULSE:
                sprite = new Pulse();
                break;

            default:
                break;
        }
        return sprite;
    }
}
