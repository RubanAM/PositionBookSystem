# PositionBookSystem

Input assumptions:
If any of these is not met Invalid input will be thrown and asked to retry the input.

1) There can be only 3 type of events BUY/SELL/CANCEL
2) Account should start with ACC
3) Security should start with SEC
4) There should be 5 values for each input line, like {ID in number} {BUY/SELL/CANCEL} {ACC} {SEC} {QUANTITY in number}

Logic:
For each input event, input validation done and added to position book before accepting next input.
If all trade events given then N should be entered to see the position book.
