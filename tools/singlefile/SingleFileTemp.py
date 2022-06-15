import re
import hashlib
import pyperclip

foxytab_str = pyperclip.paste()
save_path = r"C:\Users\scruel\OneDrive\Page\SingleFile"
page_title = foxytab_str.split('\n')[0].strip()
url = foxytab_str.split('\n')[1].strip()

sha1 = hashlib.sha1()
sha1.update(url.encode('utf-8'))
page_title = re.sub(r'[~+\\?%*:|\"<>]', '_', page_title)
page_title = page_title.strip() if len(page_title) < 150 else (page_title[:150].strip() + "...")
filename = "%s - %s.html" % (page_title.strip(), sha1.hexdigest())
print(filename)
pyperclip.copy(filename)

input("Press any key to copy markdown text.")
# markdown = "[%s](file://%s\\%s)" % (page_title, save_path, filename)
# obsidian
# markdown = "[[%s|%s]]" % (filename, page_title)
# typora
markdown = "[%s](./[page]/%s)" % (page_title, filename)
print(markdown)
pyperclip.copy(markdown)
