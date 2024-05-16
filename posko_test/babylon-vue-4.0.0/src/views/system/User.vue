<template>
    <div class="card">
        <Toast />
        <div class="card formgrid grid" style="margin-bottom: 0px;">
            <div class="field col grid" style="margin-bottom: 0px;">
                <div class="field col-12 md:col-6" style="margin-bottom: 0px;">
                    <label for="userId">사용자Id</label>
                    <AutoComplete v-model="userId" dropdown :suggestions="filteredItems" @complete="searchItems" class="w-full" />
                </div>
                <div class="field  col-12 md:col-6" style="margin-bottom: 0px;">
                    <label for="activated">사용여부</label>
                    <SelectButton v-model="activated" :options="options" aria-labelledby="basic" class="w-full" @click="setData(data)"/>
                </div>
            </div>
            <SplitButton label="Search" icon="pi pi-plus" @click="search" :model="buttonItems" class="bg-primary" />
        </div>  
        <Toolbar style="background: #ffffff; border: 0px;">
            <template #end>
                <Button icon="pi pi-refresh" aria-label="Filter" @click="refreshRow()"/>
                <span style="width: 5px;"></span>
                <Button icon="pi pi-plus" aria-label="Filter" @click="addRow()"/>
                <span style="width: 5px;"></span>
                <Button icon="pi pi-minus" aria-label="Filter"  @click="delRow()"/>
            </template>
        </Toolbar>
        <div class="grid">
            <div class="col-12 md:col-12" style="padding: 0; padding-right: 10px;">
                <ag-grid-vue
                    style="width: 100%; height: 350px;"
                    class="ag-theme-quartz"
                    :columnDefs="columns"                        
                    :rowData="userItems"
                    @grid-ready="onGridReady"
                    :rowSelection="rowSelection"
                    @cell-value-changed ="onCellValueChanged"
                    :defaultColDef="defaultColDef"
                >
                </ag-grid-vue>
            </div>

        </div>
    </div>
</template>

<script setup>
//--------------------------------------------------------------------------------------------
// imprort
//--------------------------------------------------------------------------------------------
import { useToast } from "primevue/usetoast";
import axios from '../../service/axios';
import { onBeforeMount, ref } from 'vue';
import { useI18n } from 'vue-i18n'
import RendererRowStatus from "../common/RendererRowStatus.vue";
import ags from '../../service/AgGridService';

const toast = useToast();

//--------------------------------------------------------------------------------------------
// 변수선언
//--------------------------------------------------------------------------------------------
const userItems = ref("");
const filteredItems = ref();
const userId = ref("");
const activated = ref("전체");
const options = ref(['전체', '사용', '미사용']);

let data = [];

const i18n = useI18n()
//--------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------
// ag-grid Header
//--------------------------------------------------------------------------------------------

const defaultColDef = {
    filter: true,
    enableCellChangeFlash: true,
};

const columns = [
    {
        headerName: "",
        field: "rowStatus",
        editable: false,
        pinned: 'left',
        maxWidth: 45,
        cellRenderer:  RendererRowStatus
    },
    {
        headerName: "사용자ID",
        field: "userId",
        editable: false,
    },
    {
        headerName: "사용여부",
        field: "activated",
        maxWidth: 100,
        editable: true,
    },
    {
        headerName: "사용자명",
        field: "username",
        editable: true,
    },
    {
        headerName: "패스워드",
        field: "password",
        editable: true,
    },
    {
        headerName: "별명",
        field: "nickname",
        editable: true,
    }
];
//--------------------------------------------------------------------------------------------



//--------------------------------------------------------------------------------------------
//ag-grid function
//--------------------------------------------------------------------------------------------
const rowSelection = ref(null);
const gridApi = ref();

onBeforeMount(() => {
    rowSelection.value = 'single';
});

const onCellValueChanged = (event) => {
    let res = ags.agGridChangeStatusRow(gridApi, event);
    console.log(res);
};

const onGridReady = (params) => {
    gridApi.value = params.api;

    getData();
};

const searchItems = (event) => {
    let query = event.query;
    let _filteredItems = [];

    for (let i = 0; i < userItems.value.length; i++) {
        let item = userItems.value[i];

        if (item.username.toLowerCase().indexOf(query.toLowerCase()) >= 0) {
            _filteredItems.push(item.userId + " : " + item.username );
        }
    }

    filteredItems.value = _filteredItems;
};
//--------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------
// 일반 Function
//--------------------------------------------------------------------------------------------
const getData = async () => {   

    try{    
        const response = await axios.post('/api/user/users', {});
        console.log('response => ');
        console.log(response);

        console.log('response.status = ' + response.status);
        if (response.status == 200) {
            console.log('Search Datas: ' + JSON.stringify(response.data));

            setData(response.data);

            toast.add({ severity: 'success', summary: 'Success', detail: 'Search successfully', life: 3000 });
        }
        return response.status;
    }catch(err){        
        toast.add({ severity: 'error', summary: 'Error', detail: err.message, life: 3000 });
        return err.message;
    }

};

const setData = (responseData) => {
    data = responseData; //조회데이타
    //코드타입 데이타만 업데이트
    let sActivated = activated.value==="전체"?"true,false":activated.value==="사용"?"true":"false";
    userItems.value = data.filter(rec => sActivated.indexOf(rec.activated) > -1).sort((a, b) => a.userId - b.userId) ;
    gridApi.value.sizeColumnsToFit({
        defaultMinWidth: 50
    });
}

const buttonItems = [
{
    label: 'Save',
    icon: 'pi pi-save',
    command: () => {
        saveData();
    }
},
{
    label: 'Download',
    icon: 'pi pi-file-excel',
    command: () => {
        window.location.href = 'https://vuejs.org/';
    }
}
];

const search = () => {
    getData();
};

const saveData = async () => {   

    try{    
        //rowStatus가 N,U,D인 것만 필터링
        let updatedData = ags.agGridGetData(gridApi, true);
        const saveParams = {
            //여기에 업데이트된 데이타 보내기
            searchCond: {},
            saveData: updatedData
        };
        const response = await axios.post('/api/user/saveUsers', saveParams);
        console.log('response => ');
        console.log(response);

        console.log('response.status = ' + response.status);
        if (response.status == 200) {
            console.log('Search Datas: ' + JSON.stringify(response.data));

            setData(response.data);

            toast.add({ severity: 'success', summary: 'Success', detail: 'Save successfully', life: 3000 });
        }
        return response.status;
    }catch(err){        
        toast.add({ severity: 'error', summary: 'Error', detail: err.message, life: 3000 });
        return err.message;
    }

};

const addRow = () => {
    const res = ags.agGridAddRow(gridApi, 1, false);
    console.log(res);
}

const delRow = () => {
    const selectedNodes = gridApi.value.getSelectedNodes();  //[]
    const res = ags.agGridDelRow(gridApi, selectedNodes);
    console.log(res);
}

const refreshRow = () => {
    const selectedRows = gridApi.value.getSelectedRows();
    ags.agGridEraseStatusRow(gridApi, selectedRows);
    console.log(res);
}


//--------------------------------------------------------------------------------------------

</script>
