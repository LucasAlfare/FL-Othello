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

We really expect that UI always send valid coords to avoid automatically skips current player if he "clicks" at a invalid coord, as described in this part:

```mermaid
graph TD
    E[Attempt move] --> F{Move valid?}
    F -->|Yes| I{Enemy has moves?}
    F -->|No| G{"Bad change player<br>(we don't want<br>automatically)"}
    I -->|Yes| J{"Good change"}
```