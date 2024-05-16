<template>
    <i :class="imgForRowStatus" style="font-size: 1rem"></i>
</template>

<script>
    export default {
        data() {
            return {
                rowStatus: '',
                imgForRowStatus: null
            };
        },
        methods: {
            refresh(params) {
                // this.params = params;
                this.setRowStatus(params);
            },

            setRowStatus(params) {
                this.rowStatus = params.value;
                this.imgForRowStatus = this.rowStatus === 'N' ? 'pi pi-plus' 
                                            : this.rowStatus === 'D' ? 'pi pi-trash' 
                                                : this.rowStatus === 'U' ? 'pi pi-user-edit' 
                                                    : this.rowStatus;
            }
        },
        created() {
            this.setRowStatus(this.params);
        }
    };
</script>

<style scoped></style>
