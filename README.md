```
███████╗██╗                    ██████╗ ████████╗██╗  ██╗███████╗██╗     ██╗      ██████╗ 
██╔════╝██║                   ██╔═══██╗╚══██╔══╝██║  ██║██╔════╝██║     ██║     ██╔═══██╗
█████╗  ██║         █████╗    ██║   ██║   ██║   ███████║█████╗  ██║     ██║     ██║   ██║
██╔══╝  ██║         ╚════╝    ██║   ██║   ██║   ██╔══██║██╔══╝  ██║     ██║     ██║   ██║
██║     ███████╗              ╚██████╔╝   ██║   ██║  ██║███████╗███████╗███████╗╚██████╔╝
╚═╝     ╚══════╝               ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝ ╚═════╝ 
```

My own implementation of Othello/Reversi board game.

Started developing to desktop but my code should be easy to bring to Android as well.

# Game flow

This is my suggestion for the flow of the game:

```mermaid
graph TD
    A[Start of the turn] --> B{Is human playing?}
    B -->|Yes| C[Waits for UI interaction click or coords definition]
    B -->|No| D[Process IA/Bot move]
    C --> E{Is the click/coords valid?}
    E -->|Yes| F[Executes the move]
    E -->|No| G[Gives some visual feedback]
    F --> H{Has animation?}
    H -->|Yes| I[Waits for animation end]
    H -->|No| J[Advances to next turn]
    I --> J
    D --> J
    J --> K{Is game over?}
    K -->|Yes| L[Game ends.]
    K -->|No| B
```