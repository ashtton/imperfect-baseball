<script>
    import {submitDefense} from "$lib/room.js";
    import {GAME, QUESTION_STATE} from "$lib/store.js";

    let pitchType = "FASTBALL";
    let strike = false;
</script>

<section>
    <div>
        <span>Pitch:</span> <select bind:value={pitchType}>
        <option value="FASTBALL">Fastball</option>
        <option value="CHANGEUP">Changeup</option>
        <option value="CURVEBALL">Curveball</option>
    </select>
    </div>


    <div>
        <span>Strike:</span> <input type="checkbox" bind:checked={strike}>
    </div>
    <div class="submit" on:click={() => submitDefense($GAME.gameCode, $QUESTION_STATE === "true", strike, pitchType)}>Choose Pitch</div>
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