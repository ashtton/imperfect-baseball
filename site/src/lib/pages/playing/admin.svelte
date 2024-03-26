<script>
    import {GAME, RESULT} from "$lib/store.js";
    import {nextPitch} from "$lib/room.js";
</script>

<section>
    <div class="top">
        <div class="scores">
            <div class="score"><span>Balls:</span>{$GAME.gameData.balls}</div>
            <div class="score"><span>Strikes:</span>{$GAME.gameData.strikes}</div>
            <div class="score"><span>Outs:</span>{$GAME.gameData.outs}</div>
        </div>
        <div class="scores">
            <div class="score"><span>{$GAME.homeTeam.name} {#if $GAME.gameData.bottomInning} (<i class="fa-sharp fa-solid fa-baseball-bat-ball"></i>) {/if}:</span> {$GAME.homeTeam.score} </div>
            <div class="score"><span>{$GAME.awayTeam.name} {#if !$GAME.gameData.bottomInning} (<i class="fa-sharp fa-solid fa-baseball-bat-ball"></i>) {/if}:</span> {$GAME.awayTeam.score}</div>
            <div class="score"><span>Inning:</span>{$GAME.gameData.bottomInning ? "Bottom" : "Top"} of {$GAME.gameData.inning}</div>
        </div>
    </div>

    <div class="bases">
        <div>
            <strong>1st</strong>
            {#if $GAME.gameData.runners[1]}
                <i class="fa-sharp fa-solid fa-square"></i>
            {:else}
                <i class="fa-sharp fa-regular fa-square"></i>
            {/if}
        </div>

        <div>
            <strong>2nd</strong>
            {#if $GAME.gameData.runners[2]}
                <i class="fa-sharp fa-solid fa-square"></i>
            {:else}
                <i class="fa-sharp fa-regular fa-square"></i>
            {/if}
        </div>

        <div>
            <strong>3rd</strong>
            {#if $GAME.gameData.runners[3]}
                <i class="fa-sharp fa-solid fa-square"></i>
            {:else}
                <i class="fa-sharp fa-regular fa-square"></i>
            {/if}
        </div>
    </div>

    <div class="waiting">
        <div class="bases">
            <strong>Offense:</strong>
            {#if $RESULT && $GAME.offensiveDecision}
                <span class="cap">{$GAME.offensiveDecision.swingTiming.toLowerCase().replaceAll("_", " ")}</span>
                {#if $GAME.offensiveDecision.answeredCorrectly}
                    <i class="fa-solid fa-check"></i>
                {:else}
                    <i class="fa-solid fa-x"></i>
                {/if}
            {:else}
                {$GAME.offensiveDecision ? "Pitch Selected" : "Waiting for pitch"}
            {/if}
        </div>
        <div class="bases">
            <strong>Defense:</strong>
            {#if $RESULT && $GAME.defensiveDecision}
                <span class="cap">{$GAME.defensiveDecision.pitchType.toLowerCase().replaceAll("_", " ")} for
                    {$GAME.defensiveDecision.strike ? "strike" : "ball"}</span>
                {#if $GAME.defensiveDecision.answeredCorrectly}
                    <i class="fa-solid fa-check"></i>
                {:else}
                    <i class="fa-solid fa-x"></i>
                {/if}
            {:else}
                {$GAME.defensiveDecision ? "Swing Selected" : "Waiting for swing"}
            {/if}
        </div>
    </div>


    {#if $RESULT}
        <div class="bases">
            <span class="cap">{$RESULT.toLowerCase().replaceAll("_", " ")}</span>
        </div>
        <div class="result">
            <div class="next" on:click={() => nextPitch($GAME.gameCode)}>
                Next Pitch
            </div>
        </div>
    {/if}
</section>

<style>
    .scores {
        padding: 1rem;
        border: 1px solid black;
        display: grid;
        gap: 0.5rem;
        width: 10rem;
    }

    .waiting {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 0.5rem;
    }

    .bases {
        margin-top: 1rem;
        padding: 1rem;
        border: 1px solid black;
        display: flex;
        gap: 0.2rem;
        justify-content: center;
    }

    .bases div:not(:last-child) {
        margin-right: 1rem;
    }


    .bases div {
        display: flex;
        gap: 1rem;
    }

    .top {
        display: flex;
        justify-content: space-between;
    }

    .score {
        display: flex;
        justify-content: space-between;
    }

    .score span {
        font-weight: bold;
    }

    .next {
        margin-top: 1rem;
        padding: 1rem;
        border: 1px solid black;
        display: flex;
        gap: 0.2rem;
        justify-content: center;
        width: 5rem;
        border-radius: 0.1rem;
    }

    .next:hover {
        border: none;
        background-color: lightgray;
    }

    .result {
        display: grid;
        justify-content: center;
    }

    .cap {
        text-transform: capitalize;
    }

</style>