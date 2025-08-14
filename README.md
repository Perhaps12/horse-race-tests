Small Java platformer game template currently repurposed as horse race simulation

**Features:**
- Gameloop
    - Processes and renders all the other objects and user inputs (~60fps)
- Projectile
    - System implemented that allows each projectile to be updated/rendered differently soley based on its ID
    - Each projectile is able to interact with walls, player, and all other projectiles
    - Currently projectiles are used to model each horse & the goal
- Main
    - Initialises everything when starting
- Soundplayer
    - Plays a sound effect whenever called
- Pair/padr 
    - Unecessary object that I created because I was used to pairs in c++
    - Very sketchy, was created when I wasn't familiar with OOP
- Walls
    - Stores all the walls on screen for collisions (probably unecessary)
- Player
    - Handles all player movement / abilities
    - Currently unused in the simulation so it is hidden
- Operations
    - Leftover for when I needed a specific function for projectiles and planned to make more
    - Currently unused

To run, just download everything and run main.java (place your bets)
