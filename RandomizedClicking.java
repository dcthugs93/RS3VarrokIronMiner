//I found this method while searching for something else, if you wrote this method please let me know so I can give you credit!
package scripts;

import java.awt.Point;
 
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.ChooseOption;
import org.tribot.api.rs3.ScreenModels;
import org.tribot.api.rs3.types.ScreenModel;

public class RandomizedClicking{
	
	public static ScreenModel getClosestModel(long... ids) {
		final ScreenModel[] models = ScreenModels.findNearest(ids);
        return models.length > 0 ? models[0] : null;
    }
	
	/*Added some randomization to the clicking, different amounts are needed for different sized screen models*/
    public static boolean clickScreenModel(ScreenModel s, int x1, int x2, int y1, int y2) {
		if (s != null) {
			final Point bestPoint = new Point(s.getCentrePoint().x
			+ General.random(x1, x2), s.getCentrePoint().y
            + General.random(y1, y2));
			Mouse.click(bestPoint, 1);
			return true;
		}
		return false;
	}
						  
}