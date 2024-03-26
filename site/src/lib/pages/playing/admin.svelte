<script>
    import {GAME, RESULT} from "$lib/store.js";
    import {nextPitch} from "$lib/room.js";
</script>

<section>
    <h1>inning: {$GAME.gameData.inning}/{$GAME.gameConfig.innings} {$GAME.gameData.bottomInning ? "\/" : "^"}</h1>
    <h1>away score: {$GAME.awayTeam.score}</h1>
    <h1>home score: {$GAME.homeTeam.score}</h1>
    <h3>outs: {$GAME.gameData.outs} / strikes: {$GAME.gameData.strikes} / balls: {$GAME.gameData.balls}</h3>

    {#if !$GAME.offensiveDecision}
        <h1>awaiting offense</h1>
    {/if}

    {#if !$GAME.defensiveDecision}
        <h1>awaiting defense</h1>
    {/if}

    {#if $GAME.gameData.runners[1]}
        <i class="fa-sharp fa-solid fa-square"></i>
    {:else}
        <i class="fa-sharp fa-regular fa-square"></i>
    {/if}

    {#if $GAME.gameData.runners[2]}
        <i class="fa-sharp fa-solid fa-square"></i>
    {:else}
        <i class="fa-sharp fa-regular fa-square"></i>
    {/if}

    {#if $GAME.gameData.runners[3]}
        <i class="fa-sharp fa-solid fa-square"></i>
    {:else}
        <i class="fa-sharp fa-regular fa-square"></i>
    {/if}

    {#if $RESULT}
        {$RESULT}
        <button on:click={() => nextPitch($GAME.gameCode)}>Next pitch</button>
    {/if}
</section>