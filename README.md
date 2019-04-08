# csc413-TankGame

## Student Name : Brian Lai
## Student ID : 916818167

## IDE
IntelliJ IDEA Ultimate 2019.1

## Java JDK Version
JDK 11.0.2

## Current working directory used for the game
src/

## Controls
### Player 1
* UP:     `↑`
* DOWN:   `↓`
* LEFT:   `←`
* RIGHT:  `→`
* FIRE:   `/`

### Player 2
* UP:     `W`
* DOWN:   `S`
* LEFT:   `A`
* RIGHT:  `D`
* FIRE:   `F`

## To load .csv map file
`java -jar csc413-tankgame-blai30.jar [map filename]`

Example: `java -jar csc413-tankgame-blai30.jar maps/dust2.csv`

## Available powerups
* Health: Increase current health by 2 (max: 20)
* Speed: Increase movement speed by 1 (max: 10)
* Fire Rate: Increase fire rate by 1 (max: 10)
* Damage: Increase damage by 1 (max: 10)
* Armor: Increase armor by 1 (max: 10)
* Ammo: Increase ammo by 20 (max 999)
* Fireball: Change weapon to fireball
* Boomerang: Change weapon to boomerang

## Rules
* Bullets fired from your own tank will not deal damage to itself.
* Bullets fired from your own tank that collides with other bullets fired from your own tank will not destroy each other.
* Bullets fired from your own tank that collides with your opponent's bullets will destroy each other.

### Damage formula tank A attacking tank B
`(Damage of tank A) - (Armor of tank B)`
If tank B's armor is greater than or equal to tank A's damage, tank B will always take at least 1 damage regardless of its armor.

### Strategy
Destroy crates for chance to gain powerups and get stronger. Reducing opposing tank's health to 0 will deduct a life and reset opposing tank's stats. Reduce your opponent's lives down to 0 to win the game.

### Tips
* Fireballs are great for scavenging powerups from crates since each fireball is capable of destroying up to 5 crates.
* Boomerangs will reverse direction after reaching a certain distance. Fire these in the opposite direction of your opponent to reach them if they are far.
* Ammo has a high chance of dropping from crates so don't hesitate to fire bullets.
* The orange bar above the health bar indicates firing cooldown and tells you when your next bullet is ready to be fired.
