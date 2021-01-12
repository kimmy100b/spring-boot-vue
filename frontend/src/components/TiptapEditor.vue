<template>
  <div class="editor">
    <editor-menu-bar
      :editor="editor"
      v-slot="{ commands, isActive }"
      class="menubar"
    >
      <b-col class="text-center">
        <b-button-group>
          <b-button
            class="menubar__button"
            type="button"
            :variant="getVariant(isActive.bold())"
            @click="commands.bold"
            size="sm"
          >
            <b-icon icon="type-bold" aria-hidden="true"></b-icon>
          </b-button>

          <b-button
            class="menubar__button"
            type="button"
            :variant="getVariant(isActive.italic())"
            @click="commands.italic"
            size="sm"
          >
            <b-icon icon="type-italic" aria-hidden="true"></b-icon>
          </b-button>

          <b-button
            class="menubar__button"
            type="button"
            :variant="getVariant(isActive.strike())"
            @click="commands.strike"
            size="sm"
          >
            <b-icon icon="type-strikethrough" aria-hidden="true"></b-icon>
          </b-button>

          <b-button
            class="menubar__button"
            type="button"
            :variant="getVariant(isActive.underline())"
            @click="commands.underline"
            size="sm"
          >
            <b-icon icon="type-underline" aria-hidden="true"></b-icon>
          </b-button>

          <b-button
            class="menubar__button"
            type="button"
            :variant="getVariant(isActive.heading({ level: 1 }))"
            @click="commands.heading({ level: 1 })"
            size="sm"
          >
            <b-icon icon="type-h1" aria-hidden="true"></b-icon>
          </b-button>

          <b-button
            class="menubar__button"
            type="button"
            :variant="getVariant(isActive.heading({ level: 2 }))"
            @click="commands.heading({ level: 2 })"
            size="sm"
          >
            <b-icon icon="type-h2" aria-hidden="true"></b-icon>
          </b-button>

          <b-button
            class="menubar__button"
            type="button"
            :variant="getVariant(isActive.heading({ level: 3 }))"
            @click="commands.heading({ level: 3 })"
            size="sm"
          >
            <b-icon icon="type-h3" aria-hidden="true"></b-icon>
          </b-button>

          <b-button
            class="menubar__button"
            type="button"
            :variant="getVariant(isActive.bullet_list())"
            @click="commands.bullet_list"
            size="sm"
          >
            ul
          </b-button>

          <b-button
            class="menubar__button"
            :variant="getVariant(isActive.ordered_list())"
            type="button"
            @click="commands.ordered_list"
            size="sm"
          >
            ol
          </b-button>

          <b-button
            class="menubar__button"
            :variant="getVariant(isActive.code_block())"
            type="button"
            @click="commands.code_block"
            size="sm"
          >
            <b-icon icon="code" aria-hidden="true"></b-icon>
          </b-button>

          <b-button
            class="menubar__button"
            @click="commands.horizontal_rule"
            variant="white"
            type="button"
            size="sm"
          >
            <b-icon icon="hr" aria-hidden="true"></b-icon>
          </b-button>
          <b-button
            class="menubar__button"
            variant="white"
            @click="showImageModal(commands.image)"
            size="sm"
          >
            <b-icon icon="card-image" aria-hidden="true"></b-icon>
          </b-button>
        </b-button-group>
      </b-col>
    </editor-menu-bar>

    <editor-content
      class="editor__content"
      :editor="editor"
    />
  </div>
</template>

<script>
import {Editor, EditorContent, EditorMenuBar} from 'tiptap'
import {
  Blockquote,
  CodeBlock,
  HardBreak,
  Heading,
  HorizontalRule,
  OrderedList,
  BulletList,
  ListItem,
  TodoItem,
  TodoList,
  Bold,
  Code,
  Italic,
  Link,
  Strike,
  Underline,
  History,
  Image
} from 'tiptap-extensions'

export default {
  components: {
    EditorContent,
    EditorMenuBar
  },
  data () {
    return {
      content: undefined,
      editor: undefined
    }
  },
  mounted () {
    this.editor = new Editor({
      extensions: [
        new HardBreak(),
        new Heading({ levels: [1, 2, 3] }),
        new Blockquote(),
        new BulletList(),
        new CodeBlock(),
        new HorizontalRule(),
        new ListItem(),
        new OrderedList(),
        new TodoItem(),
        new TodoList(),
        new Link(),
        new Bold(),
        new Code(),
        new Italic(),
        new Strike(),
        new Underline(),
        new History(),
        new Image()
      ],
      onUpdate: ({ getHTML }) => {
        this.content = getHTML()
      }
    })
  },
  beforeDestroy () {
    this.editor.destroy()
  },
  methods: {
    showImageModal (command) {
    },
    getVariant (isActive) {
      return isActive ? 'dark' : 'white'
    },
    setContent (content) {
      this.editor.setContent(content)
    }
  }
}
</script>

<style>
  .editor {
    height: 100%;
  }
  .menubar {
    margin-bottom: 5px;
  }
  .editor__content {
    margin-top:5px;
    height: 90%;
  }
  .ProseMirror {
    height: 100%;
    padding: 30px;
  }
  .ProseMirror:focus {
    outline: none;
  }
</style>
