# PositionBookSystem

# Input assumptions:
If any of these is not met Invalid input will be thrown and asked to retry the input.

1) There can be only 3 type of events BUY/SELL/CANCEL
2) Account should start with ACC
3) Security should start with SEC
4) There should be 5 values for each input line, like {ID in number} {BUY/SELL/CANCEL} {ACC} {SEC} {QUANTITY in number}

# Logic:
For each input event, input validation done and added to position book before accepting next input.
If all trade events given then N should be entered to see the position book.
If SELL event entered with Id which dont have associated by, then exception thrown and asked for retry event.
If CANCEL event entered with Id which dont exist before, then exception thrown and asked for retry event.
If BUY event entered with Id which is duplicate, then exception thrown and asked for retry event.

# Sample input:
Provide your input in this format: {ID in number} {BUY/SELL/CANCEL} {ACC} {SEC} {QUANTITY in number}. 
 If no more input then enter - N
1 BUY ACC1 SEC1 100
Provide your input in this format: {ID in number} {BUY/SELL/CANCEL} {ACC} {SEC} {QUANTITY in number}. 
 If no more input then enter - N
2 BUY ACC1 SEC1 6
Provide your input in this format: {ID in number} {BUY/SELL/CANCEL} {ACC} {SEC} {QUANTITY in number}. 
 If no more input then enter - N
3 SELL ACC1 SEC1 5
Provide your input in this format: {ID in number} {BUY/SELL/CANCEL} {ACC} {SEC} {QUANTITY in number}. 
 If no more input then enter - N
4 BUY ACC1 SEC2 10
Provide your input in this format: {ID in number} {BUY/SELL/CANCEL} {ACC} {SEC} {QUANTITY in number}. 
 If no more input then enter - N
4 CANCEL ACC1 SEC2 0
