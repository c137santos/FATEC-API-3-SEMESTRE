<template>
    <button
        class="cerb-btn ptb-small plr-small"
        :class="!fillType || fillType === 'full' ? 'full' : fillType === 'revert' ? 'revert' : 'mute'"
        :disabled="disabled"
    >
        <slot></slot>
    </button>
</template>

<script setup lang="ts">
import '@/assets/base.css';
type Props = {
    fillType?: 'full' | 'revert' | 'mute'
    disabled?: boolean
}

const { fillType, disabled } = defineProps<Props>()

</script>