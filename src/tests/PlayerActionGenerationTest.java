/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import mrts.PlayerAction;
import mrts.GameState;
import mrts.PhysicalGameState;
import mrts.Player;
import java.util.List;
import rts.*;
import mrts.units.Unit;
import mrts.units.UnitTypeTable;

/**
 *
 * @author santi
 */
public class PlayerActionGenerationTest {
    public static void main(String args[]) {
//        PhysicalGameState pgs = MapGenerator.bases8x8();        
//        PhysicalGameState pgs = MapGenerator.basesWorkers8x8();
//        PhysicalGameState pgs = MapGenerator.basesWorkersBarracks8x8();        
        PhysicalGameState pgs = MapGenerator.melee8x8light4();        
        GameState gs = new GameState(pgs, UnitTypeTable.utt);
        
        for(Player p:pgs.getPlayers()) {
            List<PlayerAction> pal = gs.getPlayerActions(p.getID());
            System.out.println("Player actions for " + p + ": " + pal.size() + " actions");
            for(PlayerAction pa:pal) {
                System.out.println(" - " + pa);
            }
        }
    }
    
}
