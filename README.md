```
███████╗██╗                    ██████╗ ████████╗██╗  ██╗███████╗██╗     ██╗      ██████╗ 
██╔════╝██║                   ██╔═══██╗╚══██╔══╝██║  ██║██╔════╝██║     ██║     ██╔═══██╗
█████╗  ██║         █████╗    ██║   ██║   ██║   ███████║█████╗  ██║     ██║     ██║   ██║
██╔══╝  ██║         ╚════╝    ██║   ██║   ██║   ██╔══██║██╔══╝  ██║     ██║     ██║   ██║
██║     ███████╗              ╚██████╔╝   ██║   ██║  ██║███████╗███████╗███████╗╚██████╔╝
╚═╝     ╚══════╝               ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝ ╚═════╝ 
```

My own implementation of Othello/Reversi board game.

Started developing to desktop but my code should be easy to bring to Android as well (via Compose Multiplatform).

## Game flow

This is the flow of the game:

```mermaid
graph TD
    A[Start] --> B{Playing?}
    B -->|No| C[Return false<br>Game over]
    B -->|Yes| D{Current player<br>has moves?}
    D -->|Yes| E{Attempt move}
    E --> F{Move valid?}
    F -->|Yes| G[Change player]
    F -->|No| I
    D -->|No| I{Enemy has moves?}
    I -->|Yes| G
    I -->|No| K[FinishedByNoMoves]
    K --> C
    G --> L[Return true]
    L --> A
```