<script>
    import {submitOffense} from "$lib/room.js";
    import {GAME, QUESTION_STATE} from "$lib/store.js";

    let timing = "EARLY";
</script>

<section>
    Swing: <select bind:value={timing}>
    <option value="EARLY">Early</option>
    <option value="LATE">Late</option>
    <option value="TAKE">Take</option>
</select>

    <button on:click={() => submitOffense($GAME.gameCode, $QUESTION_STATE === "true", timing)}>Submit</button>
</section>