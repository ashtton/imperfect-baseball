<script>
    import Admin from '$lib/pages/playing/admin.svelte'
    import Offense from '$lib/pages/playing/offense.svelte'
    import Defense from '$lib/pages/playing/defense.svelte'
    import Question from '$lib/pages/playing/question.svelte'

    import {DECISION, GAME, ROLE} from "$lib/store.js";
</script>

<section>
    {#if $ROLE === "ADMIN"}
        <Admin />
    {:else}
        {#if $DECISION}
            <h1>waiting..</h1>
        {:else }
            {#if $ROLE === "AWAY" && $GAME.gameData.bottomInning || $ROLE === "HOME" && !$GAME.gameData.bottomInning}
                <Defense />
            {:else}
                <Offense />
            {/if}
        {/if}
    {/if}
</section>