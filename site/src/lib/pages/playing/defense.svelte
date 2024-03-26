<script>
    import {submitDefense} from "$lib/room.js";
    import {GAME, QUESTION_STATE} from "$lib/store.js";

    let pitchType = "FASTBALL";
    let strike = false;
</script>

<section>
    Pitch: <select bind:value={pitchType}>
    <option value="FASTBALL">Fastball</option>
    <option value="CHANGEUP">Changeup</option>
    <option value="CURVEBALL">Curveball</option>

</select>

    Strike: <input type="checkbox" bind:value={strike}>
    <button on:click={() => submitDefense($GAME.gameCode, $QUESTION_STATE === "true", strike, pitchType)}>Submit</button>
</section>