---

# No Too Expensive
Removes the "Too Expensive!" anvil limit. Any operation goes through regardless of cost, and the repair penalty on the output item is reset each time so it never builds up again.

## Features
- Removes the "Too Expensive!" block on all anvil operations
- Resets the repair cost on output items so the problem does not come back
- No commands, no config, no permissions to set up
- Tiny footprint, just a single event listener running in the background

## How it works

Vanilla blocks any anvil result with a cost of 40 levels or more. NoTooExpensive listens for the anvil calculation event, detects when the cost hits that threshold, and allows the result through anyway. It also zeroes out the repair penalty on the output item so it stays usable long term.
