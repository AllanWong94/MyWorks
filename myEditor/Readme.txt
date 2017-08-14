This is an simple editor that possesses multiple features. It was completed on myself. It is capable of:

Cursor 	
The current position of the cursor is marked with a flashing vertical line.


Word wrapping 
The text editor will break text into lines such that it fits the width of the text editor window without requiring the user to scroll horizontally. The editor can also break lines between words rather than within words. Lines should only be broken in the middle of a word when the word does not fit on its own line.

Newlines 
When the user presses the Enter or Return key, the text editor will advance the cursor to the beginning of the next line.

Backspace 
Pressing the backspace key will cause the character before the current cursor position to be deleted.

Open and save 
The editor can accept a single command line argument describing the location of the file to edit. If that file exists, the editor will display the contents of that file. Pressing shortcut+s will save the current contents of the editor to that file.

Arrow keys 
Pressing any of the four arrow keys (up, down, left, right) will cause the cursor to move accordingly (e.g., the up key should move the cursor to be on the previous line at the horizontal position closest to the horizontal position of the cursor before the arrow was pressed).

Mouse input 
When the user clicks somewhere on the screen, the cursor will move to the place in the text closest to that location.

Window re-sizing 
When the user re-sizes the window, the text will be re-displayed so that it fits in the new window (e.g., if the new window is narrower or wider, the line breaks should be adjusted accordingly).

Vertical scrolling 
The text editor has a vertical scroll bar on the right side of the editor that allows the user to vertically navigate through the file. Moving the scroll bar should change the positioning of the file (but not the cursor position).

Undo and redo 
Pressing shortcut+z will undo the most recent action (either inserting a character or removing a character), and pressing shortcut+y will redo. The editor is able to undo up to 100 actions, but no more.

Changing font size 
Pressing shortcut+"+" (the shortcut key and the "+" key at the same time) will increase the font size by 4 points and pressing shortcut+"-" will decrease the font size by 4 points.

Printing the current position 
Pressing shortcut+p will print the top left coordinate of the current cursor position.



Completed on 18/05/2017 by Allan Wong.