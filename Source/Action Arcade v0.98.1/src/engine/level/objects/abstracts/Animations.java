package engine.level.objects.abstracts;

import engine.enums.EFighter;
import engine.enums.ESide;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Animations {

    private Animation animations[] = new Animation[8];
    private Image images[] = new Image[6];
    private boolean initialized = false;

    public Animations(EFighter fighter) throws SlickException {
        init(fighter);
    }

    private void init(EFighter fighter) throws SlickException {
        switch (fighter) {
            case SENTINEL:
                images[0] = new Image("data/images/entities/sentinel/pistol.png");
                images[1] = new Image("data/images/entities/sentinel/shotgun.png");
                images[2] = new Image("data/images/entities/sentinel/bfg9k.png");
                images[3] = new Image("data/images/entities/sentinel/damaged.png");
                images[4] = new Image("data/images/entities/sentinel/throw.png");
                images[5] = new Image("data/images/entities/sentinel/die.png");
                animations[0] = getAnimation("stay", "data/images/entities/sentinel/", 4, 150, false);
                animations[1] = getAnimation("stay", "data/images/entities/sentinel/", 4, 150, true);
                animations[2] = getAnimation("jump", "data/images/entities/sentinel/", 3, 150, false);
                animations[3] = getAnimation("jump", "data/images/entities/sentinel/", 3, 150, true);
                animations[4] = getAnimation("walk", "data/images/entities/sentinel/", 3, 150, false);
                animations[5] = getAnimation("walk", "data/images/entities/sentinel/", 4, 150, true);
                animations[6] = getAnimation(images[3], 500, false);
                animations[7] = getAnimation(images[3], 500, true);
                initialized = true;
                break;
            case EROBOT:
                animations[0] = null;
                animations[1] = null;
                animations[2] = null;
                animations[3] = null;
                animations[4] = getAnimation("walk", "data/images/entities/erobot/", 3, 150, false);
                animations[5] = getAnimation("walk", "data/images/entities/erobot/", 3, 150, true);
                images[5] = new Image("data/images/entities/erobot/died.png");
                initialized = true;
                break;
            case ZEROGOLEM:
                animations[0] = null;
                animations[1] = null;
                animations[2] = null;
                animations[3] = null;
                animations[4] = getAnimation("walk", "data/images/entities/zerogolem/", 6, 150, false);
                animations[5] = getAnimation("walk", "data/images/entities/zerogolem/", 6, 150, true);
                images[5] = new Image("data/images/entities/zerogolem/died.png");
                initialized = true;
                break;
        }
    }

    public Animation getStayAnimation(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return animations[0];
            }
            if (side == ESide.LEFT) {
                return animations[1];
            }
        }
        return null;
    }

    public Animation getJumpAnimation(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return animations[2];
            }
            if (side == ESide.LEFT) {
                return animations[3];
            }
        }
        return null;
    }

    public Animation getWalkAnimation(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return animations[4];
            }
            if (side == ESide.LEFT) {
                return animations[5];
            }
        }
        return null;
    }

    public Animation getPistolFigure(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return getAnimation(images[0], 500, false);
            }
            if (side == ESide.LEFT) {
                return getAnimation(images[0], 500, true);
            }

        }
        return null;
    }

    public Animation getShotgunFigure(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return getAnimation(images[1], 500, false);
            }
            if (side == ESide.LEFT) {
                return getAnimation(images[1], 500, true);
            }
        }
        return null;
    }

    public Animation getBFG9KFigure(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return getAnimation(images[2], 500, false);
            }
            if (side == ESide.LEFT) {
                return getAnimation(images[2], 500, true);
            }
        }
        return null;
    }

    public Animation getDamagedFigure(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return animations[6];
            }
            if (side == ESide.LEFT) {
                return animations[7];
            }
        }
        return null;
    }

    public Animation getThrowingFigure(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return getAnimation(images[4], 500, false);
            }
            if (side == ESide.LEFT) {
                return getAnimation(images[4], 500, true);
            }
        }
        return null;
    }

    public Animation getDiedFigure(ESide side) {
        if (initialized) {
            if (side == ESide.RIGHT) {
                return getAnimation(images[5], 500, false);
            }
            if (side == ESide.LEFT) {
                return getAnimation(images[5], 500, true);
            }
        }
        return null;
    }

    private Animation getAnimation(String figure, String path, int number,
            int duration, boolean flip) throws SlickException {
        Image frames[] = new Image[number];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new Image(path + figure + "_" + (i + 1) + ".png");
            if (flip) {
                frames[i] = frames[i].getFlippedCopy(true, false);
            }
        }
        return new Animation(frames, duration);
    }

    private Animation getAnimation(Image image, int duration, boolean flip) {
        Image frames[] = new Image[1];
        frames[0] = image;
        if (flip) {
            frames[0] = frames[0].getFlippedCopy(true, false);
        }
        return new Animation(frames, duration);
    }

    public boolean isWalkAnimation(Animation animation) {
        if (animation.equals(animations[4])) {
            return true;
        }
        if (animation.equals(animations[5])) {
            return true;
        }
        return false;
    }

    public boolean isJumpAnimation(Animation animation) {
        if (animation.equals(animations[2])) {
            return true;
        }
        if (animation.equals(animations[3])) {
            return true;
        }
        return false;
    }

    public boolean isStayAnimation(Animation animation) {
        if (animation.equals(animations[0])) {
            return true;
        }
        if (animation.equals(animations[1])) {
            return true;
        }
        return false;
    }

    public boolean isDamagedAnimation(Animation animation) {
        if (animation.equals(animations[6])) {
            return true;
        }
        if (animation.equals(animations[7])) {
            return true;
        }
        return false;
    }
}
