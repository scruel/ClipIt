import org.junit.Test;
import pers.scruel.util.PropertiesUtils;

import static org.junit.Assert.assertEquals;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */

public class DebugTest {

  @Test
  public void produced() {
    assertEquals(false, PropertiesUtils.isDebug());
  }
}
