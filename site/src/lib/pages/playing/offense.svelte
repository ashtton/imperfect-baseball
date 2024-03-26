<script>
    import {submitOffense} from "$lib/room.js";
    import {GAME, QUESTION_STATE} from "$lib/store.js";

    let timing = "EARLY";
</script>

<section>
    <div>
        Swing: <select bind:value={timing}>
        <option value="EARLY">Early</option>
        <option value="LATE">Late</option>
        <option value="TAKE">Take</option>
    </select>
    </div>

    <div class="submit" on:click={() => submitOffense($GAME.gameCode, $QUESTION_STATE === "true", timing)}>Choose Swing</div>
</section>

<style>
    section {
        display: grid;
        gap: 0.5rem;
    }

    span {
        font-size: 1.3rem;
    }

    .submit {
        text-align: center;
        background-color: gray;
        border-radius: 0.1rem;
        padding: 0.5rem;
        cursor: pointer;
    }

    .submit:hover {
        background-color: lightgray;
    }
</style>