import Vue from 'vue'

// fort-awesome
import {library} from '@fortawesome/fontawesome-svg-core'
import {
    faThumbsUp as fasThumbsUp,
    faAngleRight,
    faAngleLeft,
    faAngleDoubleRight,
    faAngleDoubleLeft
} from '@fortawesome/free-solid-svg-icons'
import {faThumbsUp as farThumbsUp, faCommentDots} from '@fortawesome/free-regular-svg-icons'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

library.add(
    fasThumbsUp,
    farThumbsUp,
    faCommentDots,
    faAngleRight,
    faAngleLeft,
    faAngleDoubleRight,
    faAngleDoubleLeft
)

Vue.component('font-awesome-icon', FontAwesomeIcon)
