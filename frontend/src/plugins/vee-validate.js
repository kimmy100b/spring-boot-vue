import Vue from 'vue'

// vee-validate
import {
    ValidationObserver,
    ValidationProvider,
    extend,
    localize
} from 'vee-validate'
import ko from 'vee-validate/dist/locale/ko.json'
import * as rules from 'vee-validate/dist/rules'

Object.keys(rules).forEach(rule => {
    extend(rule, rules[rule])
})
localize('ko', ko)
Vue.component('ValidationObserver', ValidationObserver)
Vue.component('ValidationProvider', ValidationProvider)
