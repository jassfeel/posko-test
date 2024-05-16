<template>
    <i class="fa fa-address-card" aria-hidden="true"></i>
</template>

<script>
    export default {
        data() {
            return {
                modified: 'none',
                imgForModified: null
            };
        },
        methods: {
            refresh(params) {
                this.params = params;
                this.setMood(params);
            },

            setMood(params) {
                this.mood = params.value;
                this.imgForModified = 'https://www.ag-grid.com/example-assets/smileys/' + (this.modified === 'N' ? 'fa fa-plus' : this.modified === 'D' ? 'fa fa-minus' : this.modified === 'U' ? 'fa fa-refresh' : '');
            }
        },
        created() {
            this.setMood(this.params);
        }
    };
</script>

<style scoped></style>
