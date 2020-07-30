import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '../components/searching-bar-component.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '../components/menu-component.js';
import '@vaadin/vaadin-upload/src/vaadin-upload.js';

class CreateRecipeView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-horizontal-layout style="width: 100%; height: 100%;">
 <vaadin-vertical-layout class="sidebar" style="flex-basis: calc(7*var(--lumo-size-s)); flex-shrink: 0; background-color: var(--lumo-contrast-5pct);">
  <menu-component style="flex: 0; width: 100%; flex-shrink: 0; flex-grow: 1;" id="menuComponent"></menu-component>
 </vaadin-vertical-layout>
 <vaadin-vertical-layout style="flex-grow: 1; flex-shrink: 0; flex-basis: auto; align-self: stretch;" id="vaadinVerticalLayout">
  <vaadin-vertical-layout style="flex-basis: calc(5*var(--lumo-size-s)); width: 100%; height: 100%; align-self: center;">
   <vaadin-upload style="flex-shrink: 1; flex-grow: 1; align-self: center;" id="vaadinUpload"></vaadin-upload>
  </vaadin-vertical-layout>
  <vaadin-horizontal-layout style="align-self: stretch; flex-shrink: 0; flex-grow: 0;background-color: var(--lumo-shade-20pct);" id="vaadinHorizontalLayout"></vaadin-horizontal-layout>
  <vaadin-horizontal-layout style="align-self: stretch; flex-grow: 1;">
   <searching-bar-component id="searchingBarComponent"></searching-bar-component>
   <vaadin-vertical-layout style="width: 100%;">
    <vaadin-text-field style="align-self: stretch; flex-shrink: 0; margin: var(--lumo-space-s);" label="Recipe name:" id="recipeName"></vaadin-text-field>
    <vaadin-text-area label="Recipe content:" style="align-self: stretch; flex-shrink: 1; margin: var(--lumo-space-s); height: 100%; flex-grow: 0; flex: 1;" id="recipeContent"></vaadin-text-area>
    <vaadin-button theme="primary success" style="align-self: center; flex-shrink: 0; flex: 1; flex-grow: 0;" id="vaadinButton">
      Save recipe 
    </vaadin-button>
   </vaadin-vertical-layout>
  </vaadin-horizontal-layout>
  <vaadin-vertical-layout style="width: 100%; flex-basis: calc(1*var(--lumo-size-s));"></vaadin-vertical-layout>
 </vaadin-vertical-layout>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'create-recipe-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(CreateRecipeView.is, CreateRecipeView);
