<script>
    import {onMount} from "svelte";
    import {socket} from "$lib/socket.js";
    import {GAME, USERNAME} from "$lib/store.js";

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

{#if $GAME.homeTeam && $GAME.awayTeam}
    <button>Start game</button>
{/if}