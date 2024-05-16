<template>
    <div class="card">
        <Toast />
        <div class="card formgrid grid" style="margin-bottom: 0px;">
            <div class="field col grid" style="margin-bottom: 0px;">
                <div class="field col-12 md:col-6" style="margin-bottom: 0px;">
                    <label for="username">사용자명</label>
                    <AutoComplete v-model="username" dropdown :suggestions="filteredItems" @complete="searchItems" class="w-full" />
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
                    class="ag-theme-balham"
                    :columnDefs="columns"                        
                    :rowData="userAuthorityItems"
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
const userAuthorityItems = ref("");
const filteredItems = ref();
const username = ref("");

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

const userNames = ref([]);
const authorities = ref([]);

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
        maxWidth: 150,
    },
    {
        headerName: "사용자명",
        field: "username",
        editable: true,
        cellEditor: "agSelectCellEditor",
        cellEditorParams: { values: userNames.value },
    },
    {
        headerName: "권한명",
        field: "authorityName",
        editable: true,
        cellEditor: "agSelectCellEditor",
        cellEditorParams: { values: authorities.value },
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

    for (let i = 0; i < userAuthorityItems.value.length; i++) {
        let item = userAuthorityItems.value[i];

        if (item.username.toLowerCase().indexOf(query.toLowerCase()) >= 0) {
            _filteredItems.push(item.username);
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
        const response = await axios.post('/api/user/usersAuthority', {
            username: username.value
        });
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
    data = responseData; //조회데이타(userAuthority, userList, authorityList)

    //리스트에 새로운 배열을 셋팅해서 넣으면 컬럼의 값과 연결이 되지 않기 때문에 기존의 배열을 조작해서 넣어야 연결이 된다.
    userNames.value.splice(0);
    data.userList.map(rec => userNames.value.push(rec.username));
    authorities.value.splice(0);
    data.authorityList.map(rec => authorities.value.push(rec.authority_name));

    //데이타 셋팅
    userAuthorityItems.value = data.userAuthority.sort((a, b) => a.userId - b.userId) ;

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
            searchCond: {"username": username},
            saveData: updatedData
        };
        const response = await axios.post('/api/user/saveUsersAuthority', saveParams);
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
