<template>
    <ul v-if="items" role="menu">
        <template v-for="(item, i) of items">
            <li
                :key="item.label || i"
                role="none"
                v-if="visible(item) && !item.separator"
                :class="[
                    {
                        'active-rootmenuitem': activeIndex === i && root && layoutMode === 'horizontal' && !item.disabled,
                        'active-menuitem': 
                            (
                                ($route.meta.breadcrumb && item.label === $route.meta.breadcrumb[0].parent && layoutMode === 'horizontal')
                                || ($route.meta.breadcrumb && item.label === $route.meta.breadcrumb[0].label && layoutMode === 'horizontal')
                                || (activeIndex === i && layoutMode !== 'horizontal')
                                || (activeIndex === i && !root && layoutMode === 'horizontal')
                            ) 
                            && !item.disabled,
                    },
                ]"
            >
                <router-link
                    v-if="item.to"
                    :to="item.to"
                    :style="item.style"
                    :class="[item.class, 'p-ripple', { 'p-disabled': item.disabled }]"
                    :target="item.target"
                    exact
                    @click="onMenuItemClick($event, item, i)"
                    @mouseenter="onMenuItemMouseEnter(i)"
                    role="menuitem"
                    v-ripple
                >
                    <i :class="['layout-menuitem-icon', item.icon]"></i>
                    <span v-if="item.i18n" class="layout-menuitem-text">{{ $t(item.i18n) }}</span>
                    <span v-else-if="item" class="layout-menuitem-text">{{ item.label }}</span>
                    <i v-if="item.items" class="pi pi-fw pi-angle-down layout-submenu-toggler"></i>
                    <Badge v-if="item.badge" :value="item.badge"></Badge>
                </router-link>
                <a
                    v-if="!item.to"
                    :href="item.url || '#'"
                    :style="item.style"
                    :class="[item.class, 'p-ripple', { 'p-disabled': item.disabled }]"
                    :target="item.target"
                    @mouseenter="onMenuItemMouseEnter(i)"
                    @click="onMenuItemClick($event, item, i)"
                    role="menuitem"
                    v-ripple
                >
                    <i :class="['layout-menuitem-icon', item.icon]"></i>
                    <span v-if="item.i18n" class="layout-menuitem-text">{{ $t(item.i18n) }}</span>
                    <span v-else-if="item" class="layout-menuitem-text">{{ item.label }}</span>
                    <i v-if="item.items" class="pi pi-fw pi-angle-down layout-submenu-toggler"></i>
                    <Badge v-if="item.badge" :value="item.badge"></Badge>
                </a>
                <transition name="layout-submenu-container">
                    <appsubmenu
                        v-show="activeIndex === i"
                        :items="visible(item) && item.items"
                        :menuActive="menuActive"
                        :layoutMode="layoutMode"
                        :parentMenuItemActive="activeIndex === i"
                        @menuitem-click="$emit('menuitem-click', $event)"
                    ></appsubmenu>
                </transition>
            </li>
            <li class="p-menu-separator" :style="item.style" v-if="visible(item) && item.separator" :key="'separator' + i" role="separator"></li>
        </template>
    </ul>
</template>
<script>
import EventBus from './event-bus';

export default {
    name: 'appsubmenu',
    emits: ['menuitem-click', 'root-menuitem-click'],
    props: {
        items: Array,
        layoutMode: String,
        menuActive: Boolean,
        root: {
            type: Boolean,
            default: false,
        },
        parentMenuItemActive: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            activeIndex: null,
        };
    },
    mounted() {
        EventBus.on('reset-active-index', () => {
            if (this.layoutMode === 'horizontal') {
                this.activeIndex = null;
            }
        });
    },
    methods: {
        onMenuItemClick(event, item, index) {
            if (item.disabled) {
                event.preventDefault();
                return;
            }

            //execute command
            if (item.command) {
                item.command({ originalEvent: event, item: item });
                event.preventDefault();
            }

            this.activeIndex = this.activeIndex === index ? null : index;

            if (item.items) {
                event.preventDefault();
            }

            if (this.root) {
                this.$emit('root-menuitem-click', {
                    originalEvent: event,
                });
            } else {
                const ink = this.getInk(event.currentTarget);
                if (ink) {
                    this.removeClass(ink, 'p-ink-active');
                }
            }

            this.$emit('menuitem-click', {
                originalEvent: event,
                item: item,
            });
        },
        getInk(el) {
            for (let i = 0; i < el.children.length; i++) {
                if (typeof el.children[i].className === 'string' && el.children[i].className.indexOf('p-ink') !== -1) {
                    return el.children[i];
                }
            }
            return null;
        },
        removeClass(element, className) {
            if (element.classList) element.classList.remove(className);
            else element.className = element.className.replace(new RegExp('(^|\\b)' + className.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
        },
        isMobile() {
            return window.innerWidth <= 991;
        },
        onMenuItemMouseEnter(index) {
            if (this.root && this.menuActive && this.layoutMode === 'horizontal' && !this.isMobile()) {
                this.activeIndex = index;
            }
        },
        visible(item) {
            return typeof item.visible === 'function' ? item.visible() : item.visible !== false;
        },
    },
};
</script>

<style scoped></style>
