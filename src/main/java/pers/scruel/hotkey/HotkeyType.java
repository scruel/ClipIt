package pers.scruel.hotkey;

import com.melloware.jintellitype.JIntellitype;

import java.util.Arrays;

/**
 * @author Scruel Tao
 * @version 1.0
 */
public enum HotkeyType {
  UPLOAD("upload", 0, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, 'V'),
  OCR("ocr", 1, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, 'O'),
  SEND_TO_KIND("sendtokindle", 2, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, 'K');

  private String name;
  private int identifier;
  private int modifier;
  private int keycode;

  HotkeyType(String name, int identifier, int modifier, int keycode) {
    this.identifier = identifier;
    this.name = name;
    this.modifier = modifier;
    this.keycode = keycode;
  }

  public static HotkeyType getHotKeyTypeByName(String name) {
    return Arrays.stream(HotkeyType.values())
        .filter(statusType -> statusType.getName().equals(name))
        .findFirst()
        .orElse(null);
  }

  public static HotkeyType getHotKeyTypeByIdentifier(int identifier) {
    return Arrays.stream(HotkeyType.values())
        .filter(statusType -> statusType.getIdentifier() == identifier)
        .findFirst()
        .orElse(null);
  }

  public static void setHotKeyTypeByIdentifier(String name, int modifier, int keycode) {
    HotkeyType hotKey = getHotKeyTypeByName(name);
    if (hotKey == null) return;
    hotKey.setModifier(modifier);
    hotKey.setKeycode(keycode);
  }

  public String getName() {
    return name;
  }

  public int getIdentifier() {
    return this.identifier;
  }

  public int getModifier() {
    return modifier;
  }

  public void setModifier(int modifier) {
    this.modifier = modifier;
  }

  public int getKeycode() {
    return keycode;
  }

  public void setKeycode(int keycode) {
    this.keycode = keycode;
  }
}
