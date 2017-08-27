import com.scruel.util.PropertiesUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Scruel on 2017/8/20.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */

public class DebugTest {

    @Test
    public void produced() {
        assertEquals(false, PropertiesUtil.isDebug());
    }


}
