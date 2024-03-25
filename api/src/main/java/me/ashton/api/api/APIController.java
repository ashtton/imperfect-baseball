package me.ashton.api.api;

import me.ashton.api.game.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class APIController {

    @PostMapping("/room/create")
    public ResponseEntity<?> createRoom() {
        Game game = new Game();
        game.setGameCode(UUID.randomUUID().toString().substring(0, 5));
        Game.getGames().put(game.getGameCode(), game);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/room/{code}/exists")
    public ResponseEntity<?> roomExists(@PathVariable("code") String code) {
        return ResponseEntity.ok(Game.getGames().containsKey(code));
    }
}
