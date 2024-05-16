<template>
    <div class="grid dashboard">

        <div class="col-12 md:col-6 lg:col-4">
            <div class="card tasks">
                <h5>Tasks</h5>
                <ul>
                    <li>
                        <Checkbox name="task" value="reports" v-model="tasksCheckbox" />
                        <span>Sales Report</span>
                        <span class="task-badge-open">Open<i class="pi pi-circle-on"></i></span>
                    </li>
                    <li>
                        <Checkbox name="task" value="invoices" v-model="tasksCheckbox" />
                        <span>Pay Invoices</span>
                        <span class="task-badge-open">Open<i class="pi pi-circle-on"></i></span>
                    </li>
                    <li>
                        <Checkbox name="task" value="party" v-model="tasksCheckbox" />
                        <span>Birthday Party</span>
                        <span class="task-badge-open">Open<i class="pi pi-circle-on"></i></span>
                    </li>
                    <li>
                        <Checkbox name="task" value="meeting" v-model="tasksCheckbox" />
                        <span>Client Meeting</span>
                        <span class="task-badge-closed">Closed<i class="pi pi-circle-on"></i></span>
                    </li>
                    <li>
                        <Checkbox name="task" value="themes" v-model="tasksCheckbox" />
                        <span>New Themes</span>
                        <span class="task-badge-closed">Closed<i class="pi pi-circle-on"></i></span>
                    </li>
                    <li>
                        <Checkbox name="task" value="ticket" v-model="tasksCheckbox" />
                        <span>Flight Ticket</span>
                        <span class="task-badge-closed">Closed<i class="pi pi-circle-on"></i></span>
                    </li>
                </ul>
            </div>
        </div>

        <div class="col-12 lg:col-8">
            <div class="card table">
                <h5>Inventory</h5>
                <DataTable :value="products" v-model:selection="selectedProduct" selectionMode="single" dataKey="id" :rows="5" style="margin-bottom: 20px" :paginator="true" responsiveLayout="scroll">
                    <Column>
                        <template #header> Logo </template>
                        <template #body="slotProps">
                            <img :src="'/demo/images/product/' + slotProps.data.image" :alt="slotProps.data.image" width="50" />
                        </template>
                    </Column>
                    <Column field="code" header="Code" :sortable="true">
                        <template #body="slotProps">
                            <span class="p-column-title">Code</span>
                            {{ slotProps.data.code }}
                        </template>
                    </Column>
                    <Column field="name" header="Name" :sortable="true">
                        <template #body="slotProps">
                            <span class="p-column-title">Name</span>
                            {{ slotProps.data.name }}
                        </template>
                    </Column>
                    <Column field="category" header="Category" :sortable="true">
                        <template #body="slotProps">
                            <span class="p-column-title">Category</span>
                            {{ slotProps.data.category }}
                        </template>
                    </Column>
                    <Column field="price" header="Price" :sortable="true">
                        <template #body="slotProps">
                            <span class="p-column-title">Price</span>
                            {{ formatCurrency(slotProps.data.price) }}
                        </template>
                    </Column>
                    <Column>
                        <template #header> View </template>
                        <template #body>
                            <Button icon="pi pi-search" type="button" class="mr-2"></Button>
                            <Button icon="pi pi-times" type="button" class="p-button-danger"></Button>
                        </template>
                    </Column>
                </DataTable>
            </div>
        </div>

        <div class="col-12 lg:col-12">
            <div class="card chart">
                <h5>Quarter Reports</h5>
                <Chart type="line" :data="chartData" :options="chartOptions" />
            </div>
        </div>


    </div>
</template>

<script>
import ProductService from '@/service/ProductService';

export default {
    data() {
        return {
            tasksCheckbox: [],
            chartData: {
                labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                datasets: [
                    {
                        label: 'Sales',
                        data: [12, 19, 3, 5, 2, 3, 9],
                        borderColor: ['#4CAF50'],
                        borderWidth: 3,
                        borderDash: [5, 5],
                        fill: false,
                        pointRadius: 3,
                        tension: 0.4,
                    },
                    {
                        label: 'Income',
                        data: [1, 2, 5, 3, 12, 7, 15],
                        backgroundColor: ['rgba(187,222,251,0.2)'],
                        borderColor: ['#00BCD4'],
                        borderWidth: 3,
                        fill: true,
                        tension: 0.4,
                    },
                    {
                        label: 'Expenses',
                        data: [7, 12, 15, 5, 3, 13, 21],
                        borderColor: ['#e91e63'],
                        borderWidth: 3,
                        fill: false,
                        pointRadius: [4, 6, 4, 12, 8, 0, 4],
                        tension: 0.4,
                    },
                    {
                        label: 'New Users',
                        data: [3, 7, 2, 17, 15, 13, 19],
                        borderColor: ['#FF8F00'],
                        borderWidth: 3,
                        fill: false,
                        tension: 0.4,
                    },
                ],
            },
            chartOptions: {
                responsive: true,
                hover: {
                    mode: 'index',
                },
                scales: {
                    x: {
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Month',
                        },
                    },
                    y: {
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Value',
                        },
                    },
                },
            },
            textAreaValue: '',
            products: null,
            selectedProduct: null,
            items: [
                { label: 'Save', icon: 'pi pi-fw pi-check' },
                { label: 'Update', icon: 'pi pi-fw pi-refresh' },
                { label: 'Delete', icon: 'pi pi-fw pi-trash' },
            ],
        };
    },
    productService: null,
    created() {
        this.productService = new ProductService();
    },
    mounted() {
        this.productService.getProductsSmall().then((data) => (this.products = data));
    },
    methods: {
        formatCurrency(value) {
            return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
        },
        menuToggle(event) {
            this.$refs.menu.toggle(event);
        },
    },
};
</script>

<style scoped></style>
