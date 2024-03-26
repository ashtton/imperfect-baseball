<script>
    import {GAME, ROLE, USERNAME} from "$lib/store.js";
    import {startGame} from "$lib/room.js";
</script>

<h1>Code: {$GAME.gameCode}</h1>

{#if $GAME.awayTeam}
    <h3>Away Team: {$GAME.awayTeam.name}</h3>
{:else}
    <h3>Away Team: none</h3>
{/if}

{#if $GAME.homeTeam}
    <h3>Home Team: {$GAME.homeTeam.name}</h3>
{:else}
    <h3>Home Team: none</h3>
{/if}

{#if $GAME.homeTeam && $GAME.awayTeam && $ROLE === "ADMIN"}
    <button on:click={() => startGame($GAME.gameCode)}>Start game</button>
{/if}