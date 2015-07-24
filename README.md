# BilgeBot
This is a bot that automatically solves the bilge puzzle in Puzzle Pirates. Please keep in mind <b>it only works on Windows</b> at the moment. Multi-threaded depth searching is supported, but note that increasing the depth increases the time it takes to compute the next moves exponentially. Each single increase in depth will take approximately 60 times longer than the previous depth.

### How it works
While starting up, the bot will wait till it finds the Puzzle Pirates window. Once the window is found, the bot scans the window for the bilge puzzle, at which point it will attempt to solve it.

After finding the puzzle, the bot captures the puzzle part of the window and attempts to color-match specific pixels in the puzzle to pieces that it knows, allowing the bot to "see" what pieces are on the board. If the bot finds a piece that it isn't familiar with, it assumes the board is either still settling from the previous move or that the board is blocked by the duty report or something else.

Once the bot knows what pieces are on the board, it uses a brute-force algorithm to simulate every swap possible. If the depth is higher than 1, it will simulate another swap after the previous. After simulating a swap, the bot scores the simulated board and clears and solutions the swap may have caused, and shifts the pieces up to fill their place just as the actual puzzle does. This continues until all swaps within the given depth are performed. The highest scoring set of swaps is selected as the swaps to perform.

Lastly, and most simply is the mouse movement. The bot will fit a quadratic equation to the start and end points of the mouse movement. The bot then uses the time to tell where along the curve the mouse should be. Admittedly it's not the best solution, but so far it has been effective.

### Compiling the bot
There are only 2 requirements if you are compiling the bot on your own:
<ol>
<li> The bot requires <a href="https://github.com/twall/jna">JNA</a>, or Java Native Access to be able to keep track of the Puzzle Pirates window.
<li> The contents of the /rsc/ folder must be built into the root directory of the JAR.
</ol>

Aside from that, compile the bot as you would any other program.


### Found a problem/bug or have a feature request?
Please use the <a href="https://github.com/knoxcorner/BilgeBot/issues">issue tracker</a> to report it.

<br><br>
<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.
