<script>
    import {GAME, ROLE, USERNAME} from "$lib/store.js";
    import {startGame} from "$lib/room.js";
</script>

<section>
    <h1>Code: {$GAME.gameCode}</h1>

    <div class="teams">
        {#if $GAME.awayTeam}
            <div class="team">Away Team: {$GAME.awayTeam.name}</div>
        {:else}
            <div class="team">Away Team: ~</div>
        {/if}

        {#if $GAME.homeTeam}
            <div class="team">Home Team: {$GAME.homeTeam.name}</div>
        {:else}
            <div class="team">Home Team: ~</div>
        {/if}
    </div>

    <div class="start">
        {#if $GAME.homeTeam && $GAME.awayTeam && $ROLE === "ADMIN"}
            <button on:click={() => startGame($GAME.gameCode)}>Start Game</button>
        {/if}
    </div>
</section>
<style>
    section {
        text-align: center;
    }
    h1 {
        font-size: 2rem;
    }
    .teams {
        display: flex;
        justify-content: space-between;
        font-size: 1.2rem;
    }

    .start {
        margin-top: 1rem;
        display: flex;
        justify-content: center;
    }

    .start button {
        width: 100%;
        height: 2rem;
        border: none;
        color: unset;
        border-radius: 0.5rem;
        background-color: gray;
    }

    .start button:hover {
        cursor: pointer;
        background-color: darkgray;
    }
</style>