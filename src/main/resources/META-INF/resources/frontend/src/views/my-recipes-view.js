import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '../components/menu-component.js';

class MyRecipesView extends PolymerElement {

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
  <menu-component id="menuComponent" style="align-self: stretch;"></menu-component>
 </vaadin-vertical-layout>
 <vaadin-vertical-layout class="content" style="flex-grow: 1; flex-shrink: 1; flex-basis: auto; background-color: var(--lumo-shade-20pct);" id="vaadinVerticalLayout"></vaadin-vertical-layout>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'my-recipes-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MyRecipesView.is, MyRecipesView);
