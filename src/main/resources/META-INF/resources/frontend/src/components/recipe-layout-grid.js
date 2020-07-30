import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';

class RecipeLayoutGrid extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%; padding: var(--lumo-space-xl);">
 <vaadin-horizontal-layout id="headerLayout" style="width: 100%; flex-basis: var(--lumo-size-l); flex-shrink: 0; background-color: hsl(75, 60%, 60%);"></vaadin-horizontal-layout>
 <vaadin-horizontal-layout id="imageLayout" style="width: 100%; flex-shrink: 0; flex-grow: 1; background-color: hsl(75, 84%, 80%);"></vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="header" style="width: 100%; flex-basis: var(--lumo-size-l); flex-shrink: 0; background-color: hsl(75, 84%, 90%)" id="nameLayout"></vaadin-horizontal-layout>
 <vaadin-horizontal-layout style="width: 100%; flex-grow: 1; flex-shrink: 1; flex-basis: auto;">
  <vaadin-vertical-layout class="sidebar" style="flex-basis: calc(7*var(--lumo-size-s)); flex-shrink: 0; background-color: hsl(75, 84%, 90%)" id="ingredientsLayout"></vaadin-vertical-layout>
  <vaadin-vertical-layout class="content" style="flex-grow: 1; flex-shrink: 1; flex-basis: auto;">
   <vaadin-text-area id="recipeContent" style="flex-grow: 1; align-self: stretch;background-color: hsl(75, 84%, 90%)" readonly></vaadin-text-area>
  </vaadin-vertical-layout>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="footer" style="width: 100%; flex-basis: var(--lumo-size-l); flex-shrink: 0; background-color: hsl(75, 60%, 60%); " id="footerLayout"></vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'recipe-layout-grid';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(RecipeLayoutGrid.is, RecipeLayoutGrid);
