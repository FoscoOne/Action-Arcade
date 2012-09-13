package engine.interfaces;

import engine.enums.EItem;
import org.newdawn.slick.Image;

public interface IItemObject {

    Image getIcon();

    EItem getType();

    int getQuantity();
}
