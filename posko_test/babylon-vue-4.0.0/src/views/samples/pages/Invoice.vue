<template>
    <div>
        <Button label="Print" icon="pi pi-print" @click="printClick" style="display: block; margin-bottom: 20px; margin-left: 6px" :key="1"></Button>

        <div id="invoice-content">
            <div class="invoice-wrapper">
                <div class="invoice-content">
                    <div class="invoice-header">
                        <div class="invoice-no">
                            <h1>Invoice</h1>
                            <h3>A/3100</h3>
                        </div>
                        <div class="invoice-logo">
                            <img src="/layout/images/logo-black.png" alt="babylon-layout" />
                            <p>9137 3rd Lane California City, CA 93504.</p>
                        </div>
                    </div>
                    <div class="invoice-table-1 bill-to">
                        <div class="invoice-table-header grid grid-nogutter">
                            <div class="col-6">
                                <span>BILL TO</span>
                            </div>
                            <div class="col-3">
                                <span>DATE</span>
                            </div>
                            <div class="col-3">
                                <span>NOTES</span>
                            </div>
                        </div>
                        <div class="invoice-table-content">
                            <div class="invoice-table-row grid grid-nogutter">
                                <div class="col-6">
                                    <span>TOYOKSU SYSCOM CORPORATION 11-27, MEIEKI 4-CHROME NAKAMURA-KU, NAGOYA 450-0002 JAPAN </span>
                                </div>
                                <div class="col-3">
                                    <span>30/08/19</span>
                                </div>
                                <div class="col-3">
                                    <span>N/A</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="invoice-table-1 items">
                        <div class="invoice-table-header grid grid-nogutter">
                            <div class="col-6">
                                <span>DESCRIPTION</span>
                            </div>
                            <div class="col-2">
                                <span>#</span>
                            </div>
                            <div class="col-2">
                                <span>UNIT PRICE</span>
                            </div>
                            <div class="col-2">
                                <span>LINE TOTAL</span>
                            </div>
                        </div>
                        <div class="invoice-table-content">
                            <div class="invoice-table-row grid grid-nogutter">
                                <div class="col-6">
                                    <span>1 Year PrimeFaces Elite Subscription</span>
                                </div>
                                <div class="col-2">
                                    <span>4</span>
                                </div>
                                <div class="col-2">
                                    <span>$99.00</span>
                                </div>
                                <div class="col-2">
                                    <span>$396.00</span>
                                </div>
                            </div>
                            <div class="invoice-table-row grid grid-nogutter">
                                <div class="col-6">
                                    <span>1 Year PrimeFaces Elite Subscription</span>
                                </div>
                                <div class="col-2">
                                    <span>4</span>
                                </div>
                                <div class="col-2">
                                    <span>$99.00</span>
                                </div>
                                <div class="col-2">
                                    <span>$396.00</span>
                                </div>
                            </div>
                            <div class="invoice-table-row grid grid-nogutter">
                                <div class="col-6">
                                    <span>1 Year PrimeFaces Elite Subscription</span>
                                </div>
                                <div class="col-2">
                                    <span>4</span>
                                </div>
                                <div class="col-2">
                                    <span>$99.00</span>
                                </div>
                                <div class="col-2">
                                    <span>$396.00</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="invoice-footer grid grid-nogutter">
                        <div class="col-12 md:col-6">
                            <div class="invoice-table-2 grid grid-nogutter">
                                <div class="col-6">
                                    <div class="invoice-table-col header-col">
                                        <span>BANK</span>
                                        <span>ACCOUNT BENEFICIARY</span>
                                        <span>SWIFT</span>
                                        <span>IBAN</span>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="invoice-table-col content-col">
                                        <span>BestBank</span>
                                        <span>Edward Williams</span>
                                        <span>PJNWBXND</span>
                                        <input value="GB04420235692263866724650931" readonly="true" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 md:col-6">
                            <div class="invoice-table-2 summary grid grid-nogutter">
                                <div class="col-6">
                                    <div class="invoice-table-col header-col">
                                        <span>SUB TOTAL</span>
                                        <span>VAT</span>
                                        <hr />
                                        <span class="total">TOTAL</span>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="invoice-table-col content-col">
                                        <span>$1304.00</span>
                                        <span>$234.72</span>
                                        <hr />
                                        <span class="total">$1538.72</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    methods: {
        printClick() {
            window.print();
        },
    },
};
</script>

<style scoped></style>
